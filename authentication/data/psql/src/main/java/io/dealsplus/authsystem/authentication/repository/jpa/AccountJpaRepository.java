package io.dealsplus.authsystem.authentication.repository.jpa;

import io.dealsplus.authsystem.authentication.repository.tables.AccountRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountRecord, Long> {

    @Query("SELECT a FROM AccountRecord a WHERE a.email = :email OR a.phone = :phone")
    Optional<AccountRecord> findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);

    @Query("SELECT a FROM AccountRecord a WHERE a.email = :email")
    Optional<AccountRecord> findByEmail(@Param("email") String email);

    @Query("SELECT a FROM AccountRecord a WHERE a.phone = :phone")
    Optional<AccountRecord> findByPhone(@Param("phone") String phone);
}
