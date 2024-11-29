package io.dealsplus.authsystem.authentication.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequestApi {
    @NotEmpty(message = "Access token is required")
    private String accessToken;
}
