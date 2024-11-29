package io.dealsplus.authsystem.authorization.models;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionCreateRequestApi {
    @NotEmpty
    String action;
    @NotEmpty
    String resourceType;
    @NotNull
    Boolean isGlobalScope;
    List<Long> resourceIds;

    @AssertTrue(message = "Action should be a valid")
    @Hidden
    public boolean isValidAction() {
        return PermissionAction.fromValue(action) != null;
    }

    @AssertTrue(message = "Resource type should be a valid")
    @Hidden
    public boolean isValidResourceType() {
        return ResourceType.fromValue(resourceType) != null;
    }

    @AssertTrue(message = "Resource ids should be present if its not global scope, else should not be present")
    @Hidden
    public boolean isValidResourceIds() {
        return (isGlobalScope && (resourceIds == null || resourceIds.isEmpty()))
                || (!isGlobalScope && resourceIds != null && !resourceIds.isEmpty());
    }
}
