package io.dealsplus.authsystem.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeInput {
    private PermissionAction action;
    private ResourceType resourceType;
    private Long resourceId;
    private Long accountId;
}
