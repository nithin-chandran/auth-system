package io.dealsplus.authsystem.authentication.accessvalidation;

import io.dealsplus.authsystem.authentication.models.AccountAccess;
import io.dealsplus.authsystem.authentication.token.TokenManager;
import org.springframework.stereotype.Service;

@Service
public class AccessManager {
    private final TokenManager tokenManager;

    public AccessManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public AccountAccess validateAccess(String token) {
        return tokenManager.validateAccessToken(token);
    }
}
