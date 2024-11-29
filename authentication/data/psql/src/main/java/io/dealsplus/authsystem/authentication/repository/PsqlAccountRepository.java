package io.dealsplus.authsystem.authentication.repository;


import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.entity.AccountToken;
import io.dealsplus.authsystem.authentication.repository.jpa.AccountJpaRepository;
import io.dealsplus.authsystem.authentication.repository.jpa.AccountTokenJpaRepository;
import io.dealsplus.authsystem.authentication.repository.mappers.AccountMapper;
import io.dealsplus.authsystem.authentication.repository.mappers.AccountTokenMapper;
import io.dealsplus.authsystem.authentication.repository.tables.AccountRecord;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PsqlAccountRepository implements AccountRepository {

    private final AccountMapper accountMapper;
    private final AccountTokenMapper accountTokenMapper;
    private final AccountJpaRepository accountJpaRepository;
    private final AccountTokenJpaRepository accountTokenJpaRepository;

    @Autowired
    public PsqlAccountRepository(AccountMapper accountMapper, AccountTokenMapper accountTokenMapper, AccountJpaRepository accountJpaRepository, AccountTokenJpaRepository accountTokenJpaRepository) {
        this.accountMapper = accountMapper;
        this.accountTokenMapper = accountTokenMapper;
        this.accountJpaRepository = accountJpaRepository;
        this.accountTokenJpaRepository = accountTokenJpaRepository;
    }

    @Override
    public Account addAccount(Account account) {
        var accountTable = this.accountMapper.toAccountRecord(account);
        var newAccountTable = this.accountJpaRepository.save(accountTable);
        return this.accountMapper.toAccount(newAccountTable);
    }



    @Override
    public Optional<Account> getAccountByEmailOrPhone(String email, String phone) {
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(phone)) {
            return Optional.empty();
        }
        Optional<AccountRecord> accountTable;
        if (StringUtils.isEmpty(phone)) {
            accountTable = this.accountJpaRepository.findByEmail(email);
        } else if (StringUtils.isEmpty(email)) {
            accountTable = this.accountJpaRepository.findByPhone(phone);
        } else {
            accountTable = this.accountJpaRepository.findByEmailOrPhone(email, phone);
        }
        return accountTable.map(this.accountMapper::toAccount);

    }

    @Override
    public void addToken(AccountToken accountToken) {
        var accountTokenTable = this.accountTokenMapper.toAccountTokenRecord(accountToken);
        this.accountTokenJpaRepository.save(accountTokenTable);
    }
}
