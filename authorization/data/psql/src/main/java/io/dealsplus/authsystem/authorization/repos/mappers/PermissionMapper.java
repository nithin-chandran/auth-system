package io.dealsplus.authsystem.authorization.repos.mappers;

import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.ResourceType;
import io.dealsplus.authsystem.authorization.repos.tables.PermissionRecord;
import io.dealsplus.authsystem.authorization.repos.tables.PermissionScopeRecord;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PermissionMapper {

    public PermissionRecord toPermissionRecord(Permission permission) {
        return new PermissionRecord(
                null,
                permission.getAction().getValue(),
                permission.getResourceType().getValue(),
                permission.getIsGlobalScope()
        );
    }

    public Permission toPermission(PermissionRecord permissionRecord) {
        return new Permission(
                permissionRecord.getId(),
                PermissionAction.fromValue(permissionRecord.getAction()),
                ResourceType.fromValue(permissionRecord.getResourceType()),
                permissionRecord.getIsGlobalScope(),
                List.of()
        );
    }

    public PermissionScopeRecord toPermissionScopeRecord(Long resourceId, Long permissionId) {
        return new PermissionScopeRecord(null, permissionId, resourceId);
    }

    public List<Permission> toPermissions(List<PermissionRecord> permissionRecords, List<PermissionScopeRecord> permissionScopeRecords) {
        if (permissionScopeRecords == null) {
            return permissionRecords.stream()
                    .map(this::toPermission)
                    .toList();
        }
        Map<Long, List<Long>> permissionToScopeIds = permissionScopeRecords.stream()
                .collect(Collectors.groupingBy(
                        PermissionScopeRecord::getPermissionId,
                        Collectors.mapping(PermissionScopeRecord::getResourceId, Collectors.toList())
                ));
        return permissionRecords.stream()
                .map(permissionRecord -> {
                    var permission = toPermission(permissionRecord);
                    permission.setResourceIds(permissionToScopeIds.getOrDefault(permissionRecord.getId(), Collections.emptyList()));
                    return permission;
                })
                .toList();
    }
}
