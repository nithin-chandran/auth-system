package io.dealsplus.authsystem.authorization.permission;

import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.permission.models.PermissionCreateRequest;
import io.dealsplus.authsystem.authorization.permission.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionManager {
    private final PermissionRepository permissionRepository;

    public PermissionManager(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission createPermission(PermissionCreateRequest permissionInput) {
        if (permissionInput.getIsGlobalScope()) {
            var permissions = permissionRepository
                    .getPermissionByParams(permissionInput.getAction(), permissionInput.getResourceType(), true);
            if(!permissions.isEmpty()) {
                throw new IllegalArgumentException("Permission already exists");
            }
        }

        return permissionRepository.createPermission(toDomain(permissionInput));
    }

    public List<Permission> getPermissions(List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return List.of();
        }
        return permissionRepository.getPermissions(permissionIds);
    }

    public List<Permission> getPermissions() {
        return permissionRepository.getPermissions();
    }

    public boolean validatePermissions(List<Long> permissionIds) {
        if (permissionIds == null) {
            return true;
        }
        var permissions = permissionRepository.getPermissions(permissionIds);
        return permissions.size() == permissionIds.size();
    }

    private Permission toDomain(PermissionCreateRequest permissionInput) {
        return new Permission(null, permissionInput.getAction(),
                permissionInput.getResourceType(), permissionInput.getIsGlobalScope(),
                permissionInput.getResourceIds());
    }
}
