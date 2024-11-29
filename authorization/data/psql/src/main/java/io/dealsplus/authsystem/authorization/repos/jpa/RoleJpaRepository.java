package io.dealsplus.authsystem.authorization.repos.jpa;

import io.dealsplus.authsystem.authorization.repos.tables.RoleRecord;
import org.springframework.data.jpa.repository.JpaRepository;;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleRecord, Long> {
    Optional<RoleRecord> findByName(String name);
}
