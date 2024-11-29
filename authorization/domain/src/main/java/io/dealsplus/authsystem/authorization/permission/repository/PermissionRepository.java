package io.dealsplus.authsystem.authorization.permission.repository;

import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.ResourceType;

import java.util.List;

public interface PermissionRepository {
    Permission createPermission(Permission permission);
    List<Permission> getPermissions(List<Long> permissionIds);
    List<Permission> getPermissions();
    List<Permission> getPermissionByParams(PermissionAction action, ResourceType resourceType, boolean isGlobalScope);
}
