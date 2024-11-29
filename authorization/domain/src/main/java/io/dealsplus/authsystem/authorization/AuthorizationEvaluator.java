package io.dealsplus.authsystem.authorization;

import io.dealsplus.authsystem.authentication.models.TokenAuthentication;
import io.dealsplus.authsystem.authorization.models.AuthorizeInput;
import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.ResourceType;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AuthorizationEvaluator implements PermissionEvaluator {

    private final AuthorizationManager authorizationManager;

    public AuthorizationEvaluator(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        TokenAuthentication authContext = (TokenAuthentication) authentication;
        if (authContext == null || !authContext.isAuthenticated()) {
            return false;
        }

        return authorizationManager.authorize(mapToAuthorizeInput((Long) authContext.getPrincipal(),
                Long.valueOf(targetId.toString()), targetType, permission));
    }

    private AuthorizeInput mapToAuthorizeInput(Long accountId, Long targetId, String targetType, Object permission) {

        return new AuthorizeInput(
                PermissionAction.fromValue(permission.toString()),
                ResourceType.fromValue(targetType),
                targetId == -1 ? null : targetId,
                accountId
        );
    }
}
