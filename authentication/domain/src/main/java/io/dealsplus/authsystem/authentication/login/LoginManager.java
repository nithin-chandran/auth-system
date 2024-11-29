package io.dealsplus.authsystem.authentication.login;

import com.google.common.base.Preconditions;
import io.dealsplus.authsystem.authentication.login.builder.LoginRequestBuilder;
import io.dealsplus.authsystem.authentication.login.builder.PasswordLoginRequestBuilder;
import io.dealsplus.authsystem.authentication.login.factory.LoginStrategyFactory;
import io.dealsplus.authsystem.authentication.login.models.LoginCredentials;
import io.dealsplus.authsystem.authentication.login.models.LoginRequest;
import io.dealsplus.authsystem.authentication.login.models.LoginResponse;
import io.dealsplus.authsystem.authentication.login.models.LoginStrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    private final LoginStrategyFactory strategyFactory;

    @Autowired
    public LoginManager( LoginStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public LoginResponse login(LoginRequest request) {
        var requestBuilder = this.getRequestBuilder(request.getLoginStrategyType());
        Preconditions.checkNotNull(requestBuilder, "invalid login strategy, no builder found");

        var credentials = requestBuilder.build(request);
        var loginStrategy = this.strategyFactory.getStrategy(request.getLoginStrategyType().getValue());
        var token = loginStrategy.authenticate(credentials);


        return new LoginResponse(token.getAccessToken(), token.getRefreshToken());
    }

    private LoginRequestBuilder<? extends LoginCredentials> getRequestBuilder(LoginStrategyType strategy) {
        if (LoginStrategyType.PASSWORD_LOGIN_STRATEGY.equals(strategy)) {
            return new PasswordLoginRequestBuilder();
        }
        return null;
    }
}
