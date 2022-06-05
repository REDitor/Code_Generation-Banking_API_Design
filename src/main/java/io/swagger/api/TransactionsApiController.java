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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.time.LocalDateTime;
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

    public ResponseEntity<TransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "Transaction details", schema = @Schema()) @Valid @RequestBody CreateTransactionDTO body) {
        Transaction newTransaction = modelMapper.map(body, Transaction.class);

        newTransaction.setTransactionId(UUID.randomUUID());
        newTransaction.setTimestamp(LocalDateTime.now().toString());
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

    private boolean checkAccountOwnerAndType(Account fromAccount, Account toAccount) {

        System.out.println(
                "From: " + fromAccount.getUserID().getuserId() + "   Type: " + fromAccount.getType() +
                "\nTo: " + toAccount.getUserID().getuserId() + "   Type: " + toAccount.getType()
        );

        //check if owner is not the same AND if either one account is a savings account
        if (fromAccount.getUserID().getuserId() != toAccount.getUserID().getuserId() && (fromAccount.getType() == AccountType.ACCOUNT_TYPE_SAVINGS) || toAccount.getType() == AccountType.ACCOUNT_TYPE_SAVINGS)
            return false;

        return true;
    }

    public ResponseEntity<TransactionDepositDTO> deposit(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to deposit to", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Deposit details", schema = @Schema()) @Valid @RequestBody DepositDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TransactionDepositDTO>(objectMapper.readValue("{\n  \"PerformedByID\" : 1,\n  \"Amount\" : 11.23,\n  \"From\" : \"From\",\n  \"To\" : \"NL01INHO0000000002\",\n  \"transactionId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"timestamp\" : \"2021-03-20T09:12:28Z\"\n}", TransactionDepositDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TransactionDepositDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TransactionDepositDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionDTO>> transactionsIbanGet(@DecimalMax("34") @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime", schema = @Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom, @Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime", schema = @Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<TransactionDTO>>(objectMapper.readValue("[ {\n  \"PerformedByID\" : 1,\n  \"Amount\" : 11.23,\n  \"From\" : \"NL01INHO0000000001\",\n  \"To\" : \"NL01INHO0000000002\",\n  \"transactionId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"timestamp\" : \"2021-03-20T09:12:28Z\"\n}, {\n  \"PerformedByID\" : 1,\n  \"Amount\" : 11.23,\n  \"From\" : \"NL01INHO0000000001\",\n  \"To\" : \"NL01INHO0000000002\",\n  \"transactionId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"timestamp\" : \"2021-03-20T09:12:28Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<TransactionDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<TransactionDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TransactionWithdrawlDTO> withdraw(@Size(min = 18, max = 18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to withdraw from", required = true, schema = @Schema()) @PathVariable("iban") String iban, @Parameter(in = ParameterIn.DEFAULT, description = "Withdraw details", schema = @Schema()) @Valid @RequestBody WithdrawDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TransactionWithdrawlDTO>(objectMapper.readValue("{\n  \"PerformedByID\" : 1,\n  \"Amount\" : 11.23,\n  \"From\" : \"NL01INHO0000000002\",\n  \"To\" : \"To\",\n  \"transactionId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"timestamp\" : \"2021-03-20T09:12:28Z\"\n}", TransactionWithdrawlDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TransactionWithdrawlDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TransactionWithdrawlDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
