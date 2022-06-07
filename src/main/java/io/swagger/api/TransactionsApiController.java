package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.model.CreateTransactionDTO;
import io.swagger.model.DepositDTO;
import io.swagger.model.ErrorMessageDTO;
import io.swagger.model.TransactionDTO;
import io.swagger.model.TransactionDepositDTO;
import io.swagger.model.TransactionWithdrawlDTO;
import io.swagger.model.WithdrawDTO;
import io.swagger.model.entity.Account;
import io.swagger.model.entity.AccountType;
import io.swagger.model.entity.Transaction;
import io.swagger.repository.AccountRepository;
import io.swagger.service.TransactionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private TransactionService transactionService;

    private final ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.modelMapper = new ModelMapper();
    }

    //@PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    public ResponseEntity<TransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "Transaction details", schema = @Schema()) @Valid @RequestBody CreateTransactionDTO body) {
        Transaction newTransaction = modelMapper.map(body, Transaction.class);

        newTransaction.setTransactionId(UUID.randomUUID());
        newTransaction.setTimestamp(LocalDateTime.now());
        newTransaction.setFrom(accountRepository.findAccountByIBAN(body.getFrom()));
        newTransaction.setTo(accountRepository.findAccountByIBAN(body.getTo()));

        Account fromAccount = newTransaction.getFrom();
        Account toAccount = newTransaction.getTo();

        if (!checkAccountOwnerAndType(fromAccount, toAccount))
            return new ResponseEntity(new ErrorMessageDTO("Permission denied. You do not own this savings account."), HttpStatus.FORBIDDEN);

        // update balances
        fromAccount.setBalance(fromAccount.getBalance() - newTransaction.getAmount());
        toAccount.setBalance(toAccount.getBalance() + newTransaction.getAmount());

        //TODO: assign performedBy !?

        Transaction result = transactionService.add(newTransaction);

        TransactionDTO response = modelMapper.map(newTransaction, TransactionDTO.class);
        return new ResponseEntity<TransactionDTO>(response, HttpStatus.CREATED);
    }

    // TODO: put this method in service
    private boolean checkAccountOwnerAndType(Account fromAccount, Account toAccount) {
        //check if owner is not the same AND if either one account is a savings account
        if (fromAccount.getType() != AccountType.ACCOUNT_TYPE_SAVINGS && toAccount.getType() != AccountType.ACCOUNT_TYPE_SAVINGS)
            return true;

        return fromAccount.getUserID().getuserId() == toAccount.getUserID().getuserId();
    }

    //@PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<TransactionDepositDTO> deposit(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to deposit to", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Deposit details", schema = @Schema()) @Valid @RequestBody DepositDTO body) {
        Transaction newDeposit = modelMapper.map(body, Transaction.class);

        newDeposit.setTimestamp(LocalDateTime.now());
        newDeposit.setTo(accountRepository.findAccountByIBAN(iban));

        System.out.println("Deposit transaction: " + newDeposit);
        System.out.println("To Account: " + newDeposit.getTo());

        // update balances
        newDeposit.getTo().setBalance(newDeposit.getTo().getBalance() + newDeposit.getAmount());


        //TODO: assign performedBy !?

        Transaction result = transactionService.add(newDeposit);
        TransactionDepositDTO response = modelMapper.map(newDeposit, TransactionDepositDTO.class);

        return new ResponseEntity<TransactionDepositDTO>(response, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole('EMPLOYEE') || hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionDTO>> transactionsIbanGet(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime", schema = @Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom, @Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime", schema = @Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo) {
        List<Transaction> transactions;

        //Check if from and to date are set
        if (dateTimeFrom == null && dateTimeTo == null)
            transactions = transactionService.getAllByIBAN(iban);
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            LocalDateTime from = LocalDateTime.parse(dateTimeFrom, formatter);
            LocalDateTime to = LocalDateTime.parse(dateTimeTo, formatter);
            transactions = transactionService.getAllByIbanBetweenTimestamps(iban, from, to);
        }

        System.out.println("\nTransactions: " + transactions);

        List<TransactionDTO> transactionDTOs = new ArrayList<>();

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

    @Override
    public ResponseEntity<List<TransactionDTO>> transactionsGetByUserId(UUID userId, String dateTimeFrom, String dateTimeTo) {
        List<Transaction> transactions;

        //Check if from and to date are set
        if (dateTimeFrom == null && dateTimeTo == null)
            transactions = transactionService.getAllByUserId(userId);
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            LocalDateTime from = LocalDateTime.parse(dateTimeFrom, formatter);
            LocalDateTime to = LocalDateTime.parse(dateTimeTo, formatter);
            transactions = transactionService.getAllByUserIdBetweenTimestamps(userId, from, to);
        }

        System.out.println("\nTransactions: " + transactions);

        List<TransactionDTO> transactionDTOs = new ArrayList<>();

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

    public ResponseEntity<TransactionWithdrawlDTO> withdraw(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to withdraw from", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Withdraw details", schema = @Schema()) @Valid @RequestBody WithdrawDTO body) {
        Transaction newWithdraw = modelMapper.map(body, Transaction.class);

        newWithdraw.setTimestamp(LocalDateTime.now());
        newWithdraw.setFrom(accountRepository.findAccountByIBAN(iban));

        // update balances
        newWithdraw.getFrom().setBalance(newWithdraw.getFrom().getBalance() - newWithdraw.getAmount());


        //TODO: assign performedBy !?

        Transaction result = transactionService.add(newWithdraw);
        TransactionWithdrawlDTO response = modelMapper.map(newWithdraw, TransactionWithdrawlDTO.class);

        return new ResponseEntity<TransactionWithdrawlDTO>(response, HttpStatus.CREATED);
    }

}
