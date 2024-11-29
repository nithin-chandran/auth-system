package io.dealsplus.authsystem.authentication.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordLoginCredentials extends LoginCredentials {
    protected String password;

    public PasswordLoginCredentials(String username, String password) {
        super(LoginStrategyType.PASSWORD_LOGIN_STRATEGY, username); // Call the superclass constructor
        this.password = password;
    }
}
