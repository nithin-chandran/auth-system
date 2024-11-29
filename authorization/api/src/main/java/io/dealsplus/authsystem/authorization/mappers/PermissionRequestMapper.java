package io.dealsplus.authsystem.authorization.mappers;

import io.dealsplus.authsystem.authorization.models.PermissionAction;
import io.dealsplus.authsystem.authorization.models.PermissionCreateRequestApi;
import io.dealsplus.authsystem.authorization.models.ResourceType;
import io.dealsplus.authsystem.authorization.permission.models.PermissionCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class PermissionRequestMapper {
    public PermissionCreateRequest toPermissioncreateRequest(PermissionCreateRequestApi apiRequest) {
        return new PermissionCreateRequest(
                PermissionAction.fromValue(apiRequest.getAction()),
                ResourceType.fromValue(apiRequest.getResourceType()),
                apiRequest.getIsGlobalScope(),
                apiRequest.getResourceIds()
        );
    }
}
