package io.dealsplus.authsystem.authentication.repository.jpa;

import io.dealsplus.authsystem.authentication.repository.tables.AccountTokenRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenJpaRepository  extends JpaRepository<AccountTokenRecord, Long> {
}
