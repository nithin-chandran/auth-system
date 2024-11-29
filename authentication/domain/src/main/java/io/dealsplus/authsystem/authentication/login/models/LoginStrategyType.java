package io.dealsplus.authsystem.authentication.login.models;

import lombok.Getter;

@Getter
public enum LoginStrategyType {
    PASSWORD_LOGIN_STRATEGY("passwordLoginStrategy");

    private final String value;

    LoginStrategyType(String value) {
        this.value = value;
    }

}
