package io.swagger.api;

import io.swagger.annotations.Api;
import io.swagger.model.AccountDTO;
import io.swagger.model.dto.NewAccountDTO;
import io.swagger.model.dto.UpdateAccountDTO;
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

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-12T21:54:51.581Z[GMT]")
@Api(tags = {"Accounts"})
@RestController
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<AccountDTO> createAccount(@Parameter(in = ParameterIn.DEFAULT, description = "New customer details", schema=@Schema()) @Valid @RequestBody NewAccountDTO body) {
        // TODO
        return new ResponseEntity<AccountDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<AccountDTO> getAccount(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban) {
        // TODO
        return new ResponseEntity<AccountDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<AccountDTO> updateAccount(@Size(min=18,max=18) @Parameter(in = ParameterIn.PATH, description = "The Iban of the account", required=true, schema=@Schema()) @PathVariable("iban") String iban,@Parameter(in = ParameterIn.DEFAULT, description = "Fields that need to be updated", schema=@Schema()) @Valid @RequestBody UpdateAccountDTO body) {
        // TODO
        return new ResponseEntity<AccountDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
