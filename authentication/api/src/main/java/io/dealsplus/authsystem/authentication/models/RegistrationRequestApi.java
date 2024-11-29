package io.dealsplus.authsystem.authentication.models;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestApi {
    @NotEmpty(message = "Password is required")
    private String password;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    @AssertTrue(message = "Either email or phone must be provided")
    @Hidden
    public boolean isEmailOrPhonePresent() {
        return !StringUtils.isEmpty(email) || !StringUtils.isEmpty(phone);
    }
}
