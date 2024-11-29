package io.dealsplus.authsystem.authorization.permission.models;

import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionCreateRequest {
    PermissionAction action;
    ResourceType resourceType;
    Boolean isGlobalScope;
    List<Long> resourceIds;
}
