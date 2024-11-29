package io.dealsplus.authsystem.authorization.entity;

import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private Long id;
    private PermissionAction action;
    private ResourceType resourceType;
    private Boolean isGlobalScope;
    private List<Long> resourceIds;
}
