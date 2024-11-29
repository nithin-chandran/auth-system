package io.dealsplus.authsystem.authorization.repos;

import io.dealsplus.authsystem.authorization.account.repository.AccountRoleRepository;
import io.dealsplus.authsystem.authorization.repos.jpa.AccountRoleJpaRepository;
import io.dealsplus.authsystem.authorization.repos.mappers.AccountRoleMapper;
import io.dealsplus.authsystem.authorization.repos.tables.AccountRoleRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PsqlAccountRoleRepository implements AccountRoleRepository {

    private final AccountRoleJpaRepository accountRoleJpaRepository;
    private final AccountRoleMapper accountRoleMapper;

    public PsqlAccountRoleRepository(AccountRoleJpaRepository accountRoleJpaRepository, AccountRoleMapper accountRoleMapper) {
        this.accountRoleJpaRepository = accountRoleJpaRepository;
        this.accountRoleMapper = accountRoleMapper;
    }

    @Override
    public void addRoles(Long accountId, List<Long> roleIds) {
        var accountRoleRecords = accountRoleMapper.toAccountRoleRecords(accountId, roleIds);
        accountRoleJpaRepository.saveAll(accountRoleRecords);
    }

    @Override
    public List<Long> getRoles(Long accountId) {
        var accountRoles = accountRoleJpaRepository.findAllByAccountId(accountId);
        return accountRoles.stream().map(AccountRoleRecord::getRoleId).toList();
    }
}
