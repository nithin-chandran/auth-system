package io.dealsplus.authsystem.authorization;

import io.dealsplus.authsystem.authorization.account.AccountManager;
import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.models.AuthorizeInput;
import io.dealsplus.authsystem.authorization.models.PermissionAction;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationManager {
    private final AccountManager accountManager;

    public AuthorizationManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public boolean authorize(AuthorizeInput input) {
       var accountId = input.getAccountId();
       var permissions = accountManager.getPermissions(accountId);

        return permissions.stream().anyMatch(permission ->
                isResourceTypeMatching(input, permission) &&
                        isActionMatching(input, permission) &&
                        isScopeMatching(input, permission)
        );
    }

    private boolean isResourceTypeMatching(AuthorizeInput input, Permission permission) {
        return permission.getResourceType().equals(input.getResourceType());
    }

    private boolean isActionMatching(AuthorizeInput input, Permission permission) {
        return permission.getAction().equals(input.getAction()) ||
                permission.getAction().equals(PermissionAction.MANAGE);
    }

    private boolean isScopeMatching(AuthorizeInput input, Permission permission) {
        if (input.getResourceId() == null) {
            return permission.getIsGlobalScope();
        }
        return permission.getIsGlobalScope() ||
                permission.getResourceIds().contains(input.getResourceId());
    }

}
