package io.dealsplus.authsystem.authentication.registration;

import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.password.PasswordManager;
import io.dealsplus.authsystem.authentication.registration.models.RegistrationRequest;
import io.dealsplus.authsystem.authentication.registration.models.RegistrationResponse;
import io.dealsplus.authsystem.authentication.repository.AccountRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordBasedRegisterAccount implements RegisterAccount {
    private final PasswordManager passwordManager;
    private final AccountRepository accountRepo;

    @Autowired
    public PasswordBasedRegisterAccount(PasswordManager passwordManager, AccountRepository accountRepo) {
        this.passwordManager = passwordManager;
        this.accountRepo = accountRepo;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        String password = request.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Password is empty");
        }
        if (request.getPhone() == null && request.getEmail() == null ) {
            throw new IllegalArgumentException("Either phone or email should be present");
        }
        var existingAccount = this.accountRepo.getAccountByEmailOrPhone(request.getEmail(), request.getPhone());
        if (existingAccount.isPresent()) {
            throw new IllegalArgumentException("Account already exists for email or phone");
        }
        String passwordHash = this.passwordManager.hashPassword(password);
        var account = this.accountRepo.addAccount(this.mapToAccount(request, passwordHash));
        return new RegistrationResponse(account.getId());
    }


    private Account mapToAccount(RegistrationRequest request, String passwordHash) {
        return new Account(request.getEmail(), request.getPhone(), passwordHash);
    }
}
