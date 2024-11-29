package io.dealsplus.authsystem.authentication.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String phone;
    private String password;
    private LoginStrategyType loginStrategyType;
}
