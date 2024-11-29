package io.dealsplus.authsystem.authorization.role;

import io.dealsplus.authsystem.authorization.entity.Role;
import io.dealsplus.authsystem.authorization.permission.PermissionManager;
import io.dealsplus.authsystem.authorization.role.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleManager {

    private final RoleRepository roleRepository;
    private final PermissionManager permissionManager;

    public RoleManager(RoleRepository roleRepository, PermissionManager permissionManager) {
        this.roleRepository = roleRepository;
        this.permissionManager = permissionManager;
    }

    public Role createRole(String roleName) {
        var existingRole = roleRepository.getRole(roleName, false);
        if (existingRole != null ) {
            throw new IllegalArgumentException("Role already exists");
        }
        return roleRepository.createRole(new Role(roleName));
    }

    public List<Role> getRoles() {
        var existingRoles = roleRepository.getRoles( true);

        if (existingRoles.isEmpty()) {
            return List.of();
        }
        return existingRoles;
    }

    public List<Long> getPermissions(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }
        var existingRoles = roleRepository.getRoles(roleIds, true);

        if (existingRoles.isEmpty()) {
            return List.of();
        }
        return getAggregatedPermissions(existingRoles);
    }

    public boolean validateRoles(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        var existingRoles = roleRepository.getRoles(roleIds, false);
        return existingRoles.size() == roleIds.size();
    }

    public void addPermissionsToRole(Long roleId, List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            throw new IllegalArgumentException("Permissions are not valid");
        }
        var existingRole = roleRepository.getRole(roleId, false);
        if (existingRole == null ) {
            throw new IllegalArgumentException("Role do not exists");
        }
        if (!permissionManager.validatePermissions(permissionIds)) {
            throw new IllegalArgumentException("Permissions are not valid");
        }
        roleRepository.addPermissions(roleId, permissionIds);
    }

    private List<Long> getAggregatedPermissions(List<Role> roles) {
        return roles.stream()
                .flatMap(role -> role.getPermissionIds().stream())
                .distinct()
                .toList();
    }
}
