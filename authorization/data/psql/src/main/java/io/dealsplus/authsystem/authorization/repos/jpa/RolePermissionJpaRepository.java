package io.dealsplus.authsystem.authorization.repos.jpa;

import io.dealsplus.authsystem.authorization.repos.tables.RolePermissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionRecord, Long>  {
    List<RolePermissionRecord> findAllByRoleId(Long roleId);

    List<RolePermissionRecord> findAllByRoleIdIn(List<Long> roleIds);
}
