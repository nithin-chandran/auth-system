package io.dealsplus.authsystem.authorization.repos.jpa;

import io.dealsplus.authsystem.authorization.repos.tables.AccountRoleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRoleJpaRepository extends JpaRepository<AccountRoleRecord, Long> {
    List<AccountRoleRecord> findAllByAccountId(Long accountId);
}
