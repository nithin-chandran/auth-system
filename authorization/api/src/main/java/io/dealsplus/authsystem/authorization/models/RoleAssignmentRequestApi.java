package io.dealsplus.authsystem.authorization.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleAssignmentRequestApi {
    @NotNull(message = "Role IDs list must not be null")
    @NotEmpty(message = "Role IDs list must not be empty")
    List<Long> roleIds;
}
