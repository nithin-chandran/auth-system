package io.dealsplus.authsystem.authentication.controllers;

import io.dealsplus.authsystem.authentication.accessvalidation.AccessManager;
import io.dealsplus.authsystem.authentication.login.LoginManager;
import io.dealsplus.authsystem.authentication.login.models.LoginResponse;
import io.dealsplus.authsystem.authentication.mappers.LoginRequestMapper;
import io.dealsplus.authsystem.authentication.mappers.RegisterRequestMapper;
import io.dealsplus.authsystem.authentication.models.AccessRequestApi;
import io.dealsplus.authsystem.authentication.models.AccountAccess;
import io.dealsplus.authsystem.authentication.models.LoginRequestApi;
import io.dealsplus.authsystem.authentication.models.RegistrationRequestApi;
import io.dealsplus.authsystem.authentication.registration.RegisterAccount;
import io.dealsplus.authsystem.authentication.registration.models.RegistrationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
@Tag(name = "Authentication API", description = "Handles user registration, login, and token validation")
public class AuthController {

    private final RegisterAccount registerAccountService;
    private final LoginManager loginManager;
    private final AccessManager accessManager;
    private final RegisterRequestMapper registerRequestMapper;
    private final LoginRequestMapper loginRequestMapper;

    @Autowired
    public AuthController(RegisterAccount registerAccount, LoginManager loginManager, AccessManager accessManager, RegisterRequestMapper registerRequestMapper, LoginRequestMapper loginRequestMapper) {
        this.registerAccountService = registerAccount;
        this.loginManager = loginManager;
        this.accessManager = accessManager;
        this.registerRequestMapper = registerRequestMapper;
        this.loginRequestMapper = loginRequestMapper;
    }

    @Operation(summary = "Register a new user", description = "Registers a new user account with the given registration details.")
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerAccount(@Valid @RequestBody RegistrationRequestApi request) {
        RegistrationResponse response = registerAccountService.register(this.registerRequestMapper.toDomainRequest(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "User login", description = "Logs in a user by validating their credentials.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestApi request) {
        var response = loginManager.login(this.loginRequestMapper.toDomainRequest(request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Validate access token", description = "Validates the provided access token to check user access.")
    @PostMapping("/token/validate")
    public ResponseEntity<AccountAccess> validateToken(@Valid @RequestBody AccessRequestApi request) {
        var response = accessManager.validateAccess(request.getAccessToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
