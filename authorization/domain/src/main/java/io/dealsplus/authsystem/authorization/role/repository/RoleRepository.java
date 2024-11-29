package io.dealsplus.authsystem.authorization.role.repository;

import io.dealsplus.authsystem.authorization.entity.Role;

import java.util.List;

public interface RoleRepository {
    Role createRole(Role role);
    Role getRole(String roleName, boolean includePermissions);
    Role getRole(Long roleId, boolean includePermissions);
    List<Role> getRoles(List<Long> roleIds, boolean includePermissions);
    List<Role> getRoles(boolean includePermissions);
    void deleteRole(Long roleId);
    void addPermissions(Long roleId, List<Long> permissionIds);
}
