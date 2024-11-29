package io.dealsplus.authsystem.authorization.repos.jpa;

import io.dealsplus.authsystem.authorization.repos.tables.PermissionScopeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionScopeJpaRepository extends JpaRepository<PermissionScopeRecord, Long> {

    List<PermissionScopeRecord> findAllByPermissionIdIn(List<Long> permissionIds);
}