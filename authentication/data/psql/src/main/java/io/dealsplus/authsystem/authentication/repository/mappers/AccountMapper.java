package io.dealsplus.authsystem.authentication.repository.mappers;

import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.repository.tables.AccountRecord;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountRecord toAccountRecord(Account account) {
        return new AccountRecord(account.getId(), account.getEmail(), account.getPhone(), account.getPasswordHash());
    }

    public Account toAccount(AccountRecord accountRecord) {
        return new Account(accountRecord.getId(), accountRecord.getEmail(), accountRecord.getPhone(), accountRecord.getPasswordHash());
    }
}
