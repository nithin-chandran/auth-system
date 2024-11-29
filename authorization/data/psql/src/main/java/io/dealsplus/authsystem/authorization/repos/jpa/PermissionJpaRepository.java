package io.dealsplus.authsystem.authorization.repos.jpa;

import io.dealsplus.authsystem.authorization.repos.tables.PermissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionRecord, Long> {
    List<PermissionRecord> findAllByIdIn(List<Long> ids);

    @Query("SELECT p FROM PermissionRecord p WHERE p.action = :action and p.resourceType = :resourceType and p.isGlobalScope = :isGlobalScope")
    List<PermissionRecord> findAllByActionResourceTypeScope(@Param("action") String action, @Param("resourceType") String resourceType, @Param("isGlobalScope") boolean isGlobalScope);
}
