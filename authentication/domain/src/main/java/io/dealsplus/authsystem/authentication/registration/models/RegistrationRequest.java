package io.dealsplus.authsystem.authentication.registration.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String phone;
}
