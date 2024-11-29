package io.dealsplus.authsystem.authorization.account;

import io.dealsplus.authsystem.authorization.account.repository.AccountRoleRepository;
import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.permission.PermissionManager;
import io.dealsplus.authsystem.authorization.role.RoleManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountManager {
    private final AccountRoleRepository accountRoleRepository;
    private final RoleManager roleManager;
    private final PermissionManager permissionManager;

    public AccountManager(AccountRoleRepository accountRoleRepository, RoleManager roleManager, PermissionManager permissionManager) {
        this.accountRoleRepository = accountRoleRepository;
        this.roleManager = roleManager;
        this.permissionManager = permissionManager;
    }

    public void addRoles(Long accountId, List<Long> roleIds) {
        if (!roleManager.validateRoles(roleIds)) {
            throw new IllegalArgumentException("Roles are not valid");
        }
        this.accountRoleRepository.addRoles(accountId, roleIds);
    }

    public List<Permission> getPermissions(Long accountId) {
        var permissionIds = roleManager.getPermissions(accountRoleRepository.getRoles(accountId));
        return permissionManager.getPermissions(permissionIds);
    }
}
