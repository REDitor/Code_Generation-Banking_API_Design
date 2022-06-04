package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.CreateTransactionDTO;
import io.swagger.model.DepositDTO;
import io.swagger.model.TransactionDTO;
import io.swagger.model.TransactionDepositDTO;
import io.swagger.model.TransactionWithdrawlDTO;
import io.swagger.model.WithdrawDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-30T12:05:25.016Z[GMT]")
@RestController
@Api(tags = "Transactions")
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
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TransactionDTO>(objectMapper.readValue("{\n  \"PerformedByID\" : 1,\n  \"Amount\" : 11.23,\n  \"From\" : \"NL01INHO0000000001\",\n  \"To\" : \"NL01INHO0000000002\",\n  \"transactionId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\",\n  \"timestamp\" : \"2021-03-20T09:12:28Z\"\n}", TransactionDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TransactionDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TransactionDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TransactionDepositDTO> deposit(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to deposit to", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "Deposit details", schema=@Schema()) @Valid @RequestBody DepositDTO body) {
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

    public ResponseEntity<List<TransactionDTO>> transactionsIbanGet( @DecimalMax("34") @Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.QUERY, description = "search transaction from dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeFrom", required = false) String dateTimeFrom,@Parameter(in = ParameterIn.QUERY, description = "search transaction to dateTime" ,schema=@Schema()) @Valid @RequestParam(value = "dateTimeTo", required = false) String dateTimeTo) {
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

    public ResponseEntity<TransactionWithdrawlDTO> withdraw(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban for the account to withdraw from", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "Withdraw details", schema=@Schema()) @Valid @RequestBody WithdrawDTO body) {
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
