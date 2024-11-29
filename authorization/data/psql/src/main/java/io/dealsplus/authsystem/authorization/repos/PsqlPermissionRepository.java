package io.dealsplus.authsystem.authorization.repos;

import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.ResourceType;
import io.dealsplus.authsystem.authorization.permission.repository.PermissionRepository;
import io.dealsplus.authsystem.authorization.repos.jpa.PermissionJpaRepository;
import io.dealsplus.authsystem.authorization.repos.jpa.PermissionScopeJpaRepository;
import io.dealsplus.authsystem.authorization.repos.mappers.PermissionMapper;
import io.dealsplus.authsystem.authorization.repos.tables.PermissionRecord;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PsqlPermissionRepository implements PermissionRepository {
    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionScopeJpaRepository permissionScopeJpaRepository;
    private final PermissionMapper permissionMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public PsqlPermissionRepository(PermissionJpaRepository permissionJpaRepository, PermissionScopeJpaRepository permissionScopeJpaRepository, PermissionMapper permissionMapper) {
        this.permissionJpaRepository = permissionJpaRepository;
        this.permissionScopeJpaRepository = permissionScopeJpaRepository;
        this.permissionMapper = permissionMapper;
    }

    @Transactional
    @Override
    public Permission createPermission(Permission permission) {
        var permissionRecord = permissionMapper.toPermissionRecord(permission);

        savePermissionAndScopes(permissionRecord, permission.getResourceIds());
        permission.setId(permissionRecord.getId());
        return permission;
    }


    @Override
    public List<Permission> getPermissions(List<Long> permissionIds) {
        var permissionRecords = permissionJpaRepository.findAllByIdIn(permissionIds);
        var filteredPermissionIds = permissionRecords.stream().map(PermissionRecord::getId).toList();
        var permissionScopes = permissionScopeJpaRepository.findAllByPermissionIdIn(filteredPermissionIds);

        return permissionMapper.toPermissions(permissionRecords, permissionScopes);
    }

    @Override
    public List<Permission> getPermissions() {
        var permissionRecords = permissionJpaRepository.findAll();
        var filteredPermissionIds = permissionRecords.stream().map(PermissionRecord::getId).toList();
        var permissionScopes = permissionScopeJpaRepository.findAllByPermissionIdIn(filteredPermissionIds);

        return permissionMapper.toPermissions(permissionRecords, permissionScopes);
    }

    @Override
    public List<Permission> getPermissionByParams(PermissionAction action, ResourceType resourceType, boolean isGlobalScope) {
        var records = permissionJpaRepository.findAllByActionResourceTypeScope(action.getValue(), resourceType.getValue(), isGlobalScope);
        return permissionMapper.toPermissions(records, List.of());
    }


    private void savePermissionAndScopes(PermissionRecord permissionRecord, List<Long> resourceIds) {
        entityManager.persist(permissionRecord);
        if (resourceIds == null) {
            return;
        }
        for (Long resourceId : resourceIds) {
            var scopeRecord = permissionMapper.toPermissionScopeRecord(resourceId, permissionRecord.getId());
            entityManager.persist(scopeRecord);
        }
    }
}
