package io.dealsplus.authsystem.authorization.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateRequestApi {
    @NotEmpty(message = "Role name is required")
    private String roleName;
}
