package io.dealsplus.authsystem.authentication.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCredentials {
    protected LoginStrategyType loginStrategyType;
    protected String username;
}



