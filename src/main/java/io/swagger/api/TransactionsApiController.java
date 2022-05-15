package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.dto.CreateTransactionDTO;
import io.swagger.model.dto.DepositDTO;
import io.swagger.model.TransactionDTO;
import io.swagger.model.dto.TransactionDepositDTO;
import io.swagger.model.dto.TransactionWithdrawlDTO;
import io.swagger.model.dto.WithdrawDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")
@Api(tags = {"Transactions"})
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<TransactionDTO> createTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "Transaction details", schema=@Schema()) @Valid @RequestBody CreateTransactionDTO body) {
        // TODO

        return new ResponseEntity<TransactionDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TransactionDepositDTO> deposit(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to deposit to", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "Deposit details", schema=@Schema()) @Valid @RequestBody DepositDTO body) {
        // TODO

        return new ResponseEntity<TransactionDepositDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionDTO>> transactionsIbanGet( @DecimalMax("34") @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom,@Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo) {
        // TODO

        return new ResponseEntity<List<TransactionDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TransactionWithdrawlDTO> withdraw(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to withdraw from", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "Withdraw details", schema=@Schema()) @Valid @RequestBody WithdrawDTO body) {
        // TODO

        return new ResponseEntity<TransactionWithdrawlDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
