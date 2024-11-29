package io.dealsplus.authsystem.authorization.controllers;

import io.dealsplus.authsystem.authorization.account.AccountManager;
import io.dealsplus.authsystem.authorization.entity.Permission;
import io.dealsplus.authsystem.authorization.mappers.PermissionRequestMapper;
import io.dealsplus.authsystem.authorization.models.PermissionCreateRequestApi;
import io.dealsplus.authsystem.authorization.models.PermissionGetResponseApi;
import io.dealsplus.authsystem.authorization.permission.PermissionManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@Validated
@Tag(name = "Permission Management", description = "Handles creation and retrieval of permissions")
@SecurityRequirement(name = "bearerAuth")
public class PermissionController {
    private final PermissionManager permissionManager;
    private final AccountManager accountManager;
    private final PermissionRequestMapper permissionRequestMapper;

    public PermissionController(PermissionManager permissionManager, AccountManager accountManager, PermissionRequestMapper permissionRequestMapper) {
        this.permissionManager = permissionManager;
        this.accountManager = accountManager;
        this.permissionRequestMapper = permissionRequestMapper;
    }

    @Operation(
            summary = "Create a new permission",
            description = """
                This endpoint allows for the creation of a new permission.
                Permissions define access control rights for resources and actions.
                The user needs `write:permission:*` access to use this API.
                """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the permission to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PermissionCreateRequestApi.class),
                            examples = @ExampleObject(value =
                                    """
                                        {
                                          "action": "read",
                                          "resourceType": "role",
                                          "isGlobalScope": true
                                        }
                                    """)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Permission created successfully", content = @Content(schema = @Schema(implementation = Permission.class))),
    })
    @PreAuthorize("hasPermission(-1, 'permission', 'write')")
    @PostMapping("")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody PermissionCreateRequestApi request) {
        var permission = permissionManager.createPermission(permissionRequestMapper.toPermissioncreateRequest(request));
        return new ResponseEntity<>(permission, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get permissions",
            description = """
                Retrieves a list of permissions, which can be filtered by account ID.
                If no account ID is provided, all permissions are returned.
                The user needs `read:permission:*` access to use this API.
                """,
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "accountId",
                            description = "Optional account ID to filter permissions",
                            example = "23"
                    )
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully", content = @Content(schema = @Schema(implementation = PermissionGetResponseApi.class))),
    })
    @PreAuthorize("hasPermission(-1, 'permission', 'read')")
    @GetMapping("")
    public ResponseEntity<PermissionGetResponseApi> getPermissions(@RequestParam(name = "accountId", required = false) Long accountId) {
        List<Permission> permissionList;
        if(accountId != null) {
            permissionList = accountManager.getPermissions(accountId);
        } else {
            permissionList = permissionManager.getPermissions();
        }
        return new ResponseEntity<>(new PermissionGetResponseApi(permissionList), HttpStatus.OK);
    }
}
