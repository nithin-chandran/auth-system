package io.dealsplus.authsystem.authentication.repository;

import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.entity.AccountToken;

import java.util.Optional;

public interface AccountRepository {
    Account addAccount(Account account);
    Optional<Account> getAccountByEmailOrPhone(String email, String phone);
    void addToken(AccountToken accountToken);
}
