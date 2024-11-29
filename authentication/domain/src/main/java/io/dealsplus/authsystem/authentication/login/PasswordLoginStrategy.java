package io.dealsplus.authsystem.authentication.login;

import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.exception.AccountNotFoundException;
import io.dealsplus.authsystem.authentication.login.models.PasswordLoginCredentials;
import io.dealsplus.authsystem.authentication.password.PasswordManager;
import io.dealsplus.authsystem.authentication.repository.AccountRepository;
import io.dealsplus.authsystem.authentication.token.TokenManager;
import io.dealsplus.authsystem.authentication.token.models.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component("passwordLoginStrategy")
public class PasswordLoginStrategy implements LoginStrategy<PasswordLoginCredentials> {
    private final PasswordManager passwordManager;
    private final AccountRepository accountRepo;
    private final TokenManager tokenManager;


    @Autowired
    public PasswordLoginStrategy(PasswordManager passwordManager, AccountRepository accountRepo, TokenManager tokenManager) {
        this.passwordManager = passwordManager;
        this.accountRepo = accountRepo;
        this.tokenManager = tokenManager;
    }

    @Override
    public Token authenticate(PasswordLoginCredentials credentials) {
        var account = this.accountRepo.getAccountByEmailOrPhone(credentials.getUsername(), credentials.getUsername());
        if (account.isEmpty()) {
            throw new AccountNotFoundException("account not found for the username");
        }
        var isValid =  this.passwordManager.validatePassword(credentials.getPassword(), account.get().getPasswordHash());
        if (!isValid) {
            throw new BadCredentialsException("password is incorrect");
        }
        return tokenManager.generateToken(account.get());
    }
}
