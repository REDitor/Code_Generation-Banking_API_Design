package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.jwt.JwtTokenProvider;
import io.swagger.model.CreateTransactionDTO;
import io.swagger.model.DepositDTO;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.TransactionDTO;
import io.swagger.model.TransactionDepositDTO;
import io.swagger.model.TransactionWithdrawlDTO;
import io.swagger.model.WithdrawDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.Transaction;
import io.swagger.repository.AccountRepository;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
@Api(tags = "Transactions")
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private TransactionService transactionService;
    private UserService userService;

    private final ModelMapper modelMapper;

    private AccountRepository accountRepository;

    private JwtTokenProvider jwtTokenProvider;

    public TransactionsApiController(AccountRepository accountRepository, ObjectMapper objectMapper, HttpServletRequest request, TransactionService transactionService, UserService userService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
        this.userService = userService;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @PreAuthorize("hasRole('CUSTOMER') || hasRole('EMPLOYEE')")
    public ResponseEntity<TransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "Transaction details", schema = @Schema()) @Valid @RequestBody CreateTransactionDTO body) {
        Transaction newTransaction = modelMapper.map(body, Transaction.class);

        newTransaction.setTransactionId(UUID.randomUUID());
        newTransaction.setTimestamp(LocalDateTime.now());
        newTransaction.setFrom(accountRepository.findAccountByIBAN(body.getFrom()));
        newTransaction.setTo(accountRepository.findAccountByIBAN(body.getTo()));

        Account fromAccount = newTransaction.getFrom();
        Account toAccount = newTransaction.getTo();

        ResponseEntity failureResponse = verifyTransaction(newTransaction, fromAccount, toAccount);
        if (failureResponse != null)
            return failureResponse;

        // update balances
        fromAccount.setBalance(fromAccount.getBalance() - newTransaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + newTransaction.getAmount());

        newTransaction.setPerformedByID(userService.getLoggedUser(request));

        // save transaction
        Transaction result = transactionService.add(newTransaction);

        TransactionDTO successResponse = modelMapper.map(result, TransactionDTO.class);
        successResponse.setFrom(result.getFrom().getIBAN());
        successResponse.setTo(result.getTo().getIBAN());
        successResponse.setPerformedByID(userService.getLoggedUser(request).getuserId());

        return new ResponseEntity<TransactionDTO>(successResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<TransactionDTO>> transactionsIbanGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime", schema = @Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom, @Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime", schema = @Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo) {

        List<Transaction> transactions = this.getTransactionsByIban(iban, dateTimeFrom, dateTimeTo);
        List<TransactionDTO> transactionDTOs = new ArrayList<>();

        // fill dto with returned data
        for (Transaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO();

            transactionDTO.setTransactionId(transaction.getTransactionId());
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setFrom(transaction.getFrom().getIBAN());
            transactionDTO.setTo(transaction.getTo().getIBAN());
            transactionDTO.setTimestamp(transaction.getTimestamp().toString());
            transactionDTO.setPerformedByID(null);

            transactionDTOs.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER') || hasRole('EMPLOYEE')")
    public ResponseEntity<List<TransactionDTO>> transactionsGetByUserId(UUID userId, String dateTimeFrom, String dateTimeTo) {

        List<Transaction> transactions = this.getTransactionsByUserId(userId, dateTimeFrom, dateTimeTo);
        List<TransactionDTO> transactionDTOs = new ArrayList<>();

        // fill dto with returned data
        for (Transaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO();

            transactionDTO.setTransactionId(transaction.getTransactionId());
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setFrom(transaction.getFrom() == null ? null : transaction.getFrom().getIBAN());
            transactionDTO.setTo(transaction.getTo() == null ? null : transaction.getTo().getIBAN());
            transactionDTO.setTimestamp(transaction.getTimestamp().toString());
            transactionDTO.setPerformedByID(userService.getLoggedUser(request).getuserId());

            transactionDTOs.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TransactionDepositDTO> deposit(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to deposit to", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Deposit details", schema = @Schema()) @Valid @RequestBody DepositDTO body) {
        Transaction newDeposit = modelMapper.map(body, Transaction.class);

        newDeposit.setTimestamp(LocalDateTime.now());
        newDeposit.setTo(accountRepository.findAccountByIBAN(iban));

        ResponseEntity failureResponse = verifyDeposit(newDeposit);
        if (failureResponse != null)
            return failureResponse;

        // update balances
        newDeposit.getTo().setBalance(newDeposit.getTo().getBalance() + newDeposit.getAmount());

        newDeposit.setPerformedByID(userService.getLoggedUser(request));

        Transaction result = transactionService.add(newDeposit);
        TransactionDepositDTO successResponse = modelMapper.map(newDeposit, TransactionDepositDTO.class);
        successResponse.setTo(newDeposit.getTo().getIBAN());

        return new ResponseEntity<TransactionDepositDTO>(successResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TransactionWithdrawlDTO> withdraw(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to withdraw from", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Withdraw details", schema = @Schema()) @Valid @RequestBody WithdrawDTO body) {
        Transaction newWithdrawal = modelMapper.map(body, Transaction.class);

        newWithdrawal.setTimestamp(LocalDateTime.now());
        newWithdrawal.setFrom(accountRepository.findAccountByIBAN(iban));


        ResponseEntity failureResponse = verifyWithdrawal(newWithdrawal);
        if (failureResponse != null)
            return failureResponse;

        // update balances
        newWithdrawal.getFrom().setBalance(newWithdrawal.getFrom().getBalance() - newWithdrawal.getAmount());

        newWithdrawal.setPerformedByID(userService.getLoggedUser(request));

        Transaction result = transactionService.add(newWithdrawal);
        TransactionWithdrawlDTO successResponse = modelMapper.map(newWithdrawal, TransactionWithdrawlDTO.class);
        successResponse.setFrom(newWithdrawal.getFrom().getIBAN());

        return new ResponseEntity<TransactionWithdrawlDTO>(successResponse, HttpStatus.CREATED);
    }

    private ResponseEntity verifyTransaction(Transaction newTransaction, Account fromAccount, Account toAccount) {
        // check if amount is negative or 0
        if (transactionService.isNegativeOrZero(newTransaction.getAmount()))
            return new ResponseEntity(new ErrorMessageDTO("Amount cannot be lower than or equal to 0"), HttpStatus.NOT_ACCEPTABLE);

        // check if the account money is transferred from belongs to the logged user
        if (!userService.accountOwnerIsLoggedUser(fromAccount, request) && !userService.isEmployee(request))
            return new ResponseEntity(new ErrorMessageDTO("Permission denied: You do not own this account or do not have employee permissions."), HttpStatus.FORBIDDEN);

        // check if a savings account is involved AND if so if both accounts are owned by the same user
        if ((transactionService.isSavingsAccount(fromAccount) || transactionService.isSavingsAccount(toAccount)) && !transactionService.hasSameOwner(fromAccount, toAccount) && !userService.isEmployee(request))
            return new ResponseEntity(new ErrorMessageDTO("Permission denied: You do not own this savings account"), HttpStatus.FORBIDDEN);

        // check if transaction amount exceeds account balance
        if (transactionService.exceedsBalance(fromAccount, newTransaction.getAmount(), request))
            return new ResponseEntity(new ErrorMessageDTO("Your balance is too low"), HttpStatus.NOT_ACCEPTABLE);

        // check if transaction exceeds transaction limit
        if (transactionService.exceedsTransactionLimit(newTransaction.getAmount(), request))
            return new ResponseEntity(new ErrorMessageDTO("Transaction amount exceeds the transaction limit. Please try a lower amount"), HttpStatus.NOT_ACCEPTABLE);

        // check if transaction exceeds the daily limit
        if (transactionService.exceedsDailyLimit(newTransaction.getAmount(), request))
            return new ResponseEntity(new ErrorMessageDTO("Daily limit reached. Please try again tomorrow."), HttpStatus.NOT_ACCEPTABLE);

        return null;
    }

    private ResponseEntity verifyWithdrawal(Transaction newWithdrawal) {
        try {
            if (transactionService.isNegativeOrZero(newWithdrawal.getAmount()))
                return new ResponseEntity(new ErrorMessageDTO("Amount cannot be lower than or equal to 0"), HttpStatus.NOT_ACCEPTABLE);

            // check if the account money is transferred from belongs to the logged user
            if (!userService.accountOwnerIsLoggedUser(newWithdrawal.getFrom(), request))
                return new ResponseEntity(new ErrorMessageDTO("Permission denied: You do not own this account."), HttpStatus.UNAUTHORIZED);

            // check if a savings account is involved AND if so if both accounts are owned by the same user
            if (transactionService.isSavingsAccount(newWithdrawal.getFrom()))
                return new ResponseEntity(new ErrorMessageDTO("Permission denied: Cannot withdraw from savings account"), HttpStatus.FORBIDDEN);

            // check if transaction amount exceeds account balance
            if (transactionService.exceedsBalance(newWithdrawal.getFrom(), newWithdrawal.getAmount(), request))
                return new ResponseEntity(new ErrorMessageDTO("Your balance is too low"), HttpStatus.NOT_ACCEPTABLE);

            // check if transaction exceeds transaction limit
            if (transactionService.exceedsTransactionLimit(newWithdrawal.getAmount(), request))
                return new ResponseEntity(new ErrorMessageDTO("Transaction amount exceeds the transaction limit. Please try a lower amount"), HttpStatus.NOT_ACCEPTABLE);

            // check if transaction exceeds the daily limit
            if (transactionService.exceedsDailyLimit(newWithdrawal.getAmount(), request))
                return new ResponseEntity(new ErrorMessageDTO("Daily limit reached. Please try again tomorrow."), HttpStatus.NOT_ACCEPTABLE);
        } catch (NullPointerException npe) {
            return new ResponseEntity(new ErrorMessageDTO("Account not found: The account you are trying to deposit to does not exist"), HttpStatus.NOT_FOUND);
        }

        return null;
    }

    private ResponseEntity verifyDeposit(Transaction newDeposit) {
        try {
            if (transactionService.isNegativeOrZero(newDeposit.getAmount()))
                return new ResponseEntity(new ErrorMessageDTO("Amount cannot be lower than or equal to 0"), HttpStatus.NOT_ACCEPTABLE);

            // check if the account money is transferred from belongs to the logged user
            if (!userService.accountOwnerIsLoggedUser(newDeposit.getTo(), request))
                return new ResponseEntity(new ErrorMessageDTO("Permission denied: You do not own this account."), HttpStatus.UNAUTHORIZED);

            // check if a savings account is involved AND if so if both accounts are owned by the same user
            if (transactionService.isSavingsAccount(newDeposit.getTo()))
                return new ResponseEntity(new ErrorMessageDTO("Permission denied: Cannot deposit to savings account."), HttpStatus.FORBIDDEN);
        } catch (NullPointerException npe) {
            return new ResponseEntity(new ErrorMessageDTO("Account not found: The account you are trying to deposit to does not exist"), HttpStatus.NOT_FOUND);
        }

        return null;
    }

    private List<Transaction> getTransactionsByIban(String iban, String dateTimeFrom, String dateTimeTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        //Check if from and to date are set
        if (dateTimeFrom == null && dateTimeTo == null)
            return transactionService.getAllByIBAN(iban);
        else if (dateTimeFrom == null) {
            return transactionService.getAllByIbanBetweenTimestamps(iban, LocalDateTime.now(), LocalDateTime.parse(dateTimeTo, formatter));
        } else if (dateTimeTo == null)
            return transactionService.getAllByIbanBetweenTimestamps(iban, LocalDateTime.parse(dateTimeFrom, formatter), LocalDateTime.now());
        else {
            return transactionService.getAllByIbanBetweenTimestamps(iban, LocalDateTime.parse(dateTimeFrom, formatter), LocalDateTime.parse(dateTimeTo, formatter));
        }
    }

    private List<Transaction> getTransactionsByUserId(UUID userId, String dateTimeFrom, String dateTimeTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        //Check if from and to date are set
        if ((dateTimeFrom == null && dateTimeTo == null) || (dateTimeFrom == "" && dateTimeTo == ""))
            return transactionService.getAllByUserId(userId);
        else if (dateTimeFrom == null || dateTimeFrom == "")
            return transactionService.getAllByUserIdBetweenTimestamps(userId, LocalDateTime.parse(LocalDateTime.now().toString(), formatter), LocalDateTime.parse(dateTimeTo, formatter));
        else if (dateTimeTo == null || dateTimeTo == "") {
            String dateTimeNow = LocalDateTime.now().format(formatter);
            return transactionService.getAllByUserIdBetweenTimestamps(userId, LocalDateTime.parse(dateTimeFrom, formatter), LocalDateTime.parse(dateTimeNow, formatter));
        } else
            return transactionService.getAllByUserIdBetweenTimestamps(userId, LocalDateTime.parse(dateTimeFrom, formatter), LocalDateTime.parse(dateTimeTo, formatter));
    }
}
