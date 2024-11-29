package io.dealsplus.authsystem.authentication.login.factory;

import io.dealsplus.authsystem.authentication.login.LoginStrategy;
import io.dealsplus.authsystem.authentication.login.models.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoginStrategyFactory {

    private final Map<String, LoginStrategy<? extends LoginCredentials>> strategies;

    @Autowired
    public LoginStrategyFactory(Map<String, LoginStrategy<? extends LoginCredentials>> strategies) {
        this.strategies = strategies;
    }

    @SuppressWarnings("unchecked")
    public <T extends LoginCredentials> LoginStrategy<T> getStrategy(String method) {
        LoginStrategy<? extends LoginCredentials> strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for method: " + method);
        }
        return (LoginStrategy<T>) strategy;
    }
}
