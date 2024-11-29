package io.dealsplus.authsystem.authorization.repos.mappers;

import io.dealsplus.authsystem.authorization.entity.Role;
import io.dealsplus.authsystem.authorization.repos.tables.RolePermissionRecord;
import io.dealsplus.authsystem.authorization.repos.tables.RoleRecord;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoleMapper {
    public RoleRecord toRoleRecord(Role role) {
        return new RoleRecord(role.getId(),role.getName());
    }

    public Role toRole(RoleRecord roleRecord) {
        return new Role(roleRecord.getId(), roleRecord.getName(), null);
    }

    public List<Role> toRoles(List<RoleRecord> roleRecords) {
        return roleRecords.stream()
                .map(this::toRole)
                .toList();
    }

    public List<RolePermissionRecord> toRolePermissionRecords(Long roleId, List<Long> permissionIds) {
        return permissionIds
                .stream()
                .map(permissionId -> new RolePermissionRecord(null, roleId, permissionId))
                .toList();
    }

    public List<Role> toRoles(List<RoleRecord> roleRecords, List<RolePermissionRecord> rolePermissions) {
        if (rolePermissions == null) {
            return roleRecords.stream()
                    .map(this::toRole)
                    .toList();
        }
        Map<Long, List<Long>> roleIdToPermissionIds = rolePermissions.stream()
                .collect(Collectors.groupingBy(
                        RolePermissionRecord::getRoleId,
                        Collectors.mapping(RolePermissionRecord::getPermissionId, Collectors.toList())
                ));
        return roleRecords.stream()
                .map(roleRecord -> {
                    var role = toRole(roleRecord);
                    role.setPermissionIds(roleIdToPermissionIds.getOrDefault(roleRecord.getId(), Collections.emptyList()));
                    return role;
                })
                .toList();
    }

    public Role toRole(RoleRecord roleRecord, List<RolePermissionRecord> rolePermissionRecordList) {
        if (rolePermissionRecordList == null) {
            return new Role(roleRecord.getId(), roleRecord.getName(), null);
        }
        var permissionIds = rolePermissionRecordList
                .stream()
                .map(RolePermissionRecord::getPermissionId)
                .toList();

        return new Role(roleRecord.getId(), roleRecord.getName(), permissionIds);
    }
}
