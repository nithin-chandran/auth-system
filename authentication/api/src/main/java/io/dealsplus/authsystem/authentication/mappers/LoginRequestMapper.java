package io.dealsplus.authsystem.authentication.mappers;

import io.dealsplus.authsystem.authentication.login.models.LoginRequest;
import io.dealsplus.authsystem.authentication.login.models.LoginStrategyType;
import io.dealsplus.authsystem.authentication.models.LoginRequestApi;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestMapper {
    public LoginRequest toDomainRequest(LoginRequestApi apiRequest) {
        return new LoginRequest(
                apiRequest.getEmail(), apiRequest.getPhone(), apiRequest.getPassword(), LoginStrategyType.PASSWORD_LOGIN_STRATEGY
        );
    }
}
