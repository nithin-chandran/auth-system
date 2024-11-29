package io.dealsplus.authsystem.authorization.controllers;

import io.dealsplus.authsystem.authorization.entity.Role;
import io.dealsplus.authsystem.authorization.models.PermissionAssignmentRequestApi;
import io.dealsplus.authsystem.authorization.models.RoleCreateRequestApi;
import io.dealsplus.authsystem.authorization.models.RoleGetResponseApi;
import io.dealsplus.authsystem.authorization.role.RoleManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class RoleController {
    private final RoleManager roleManager;

    public RoleController(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Operation(
            summary = "Create a new role",
            description = "This endpoint allows users with the required permissions to create a new role.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the role to be created",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"roleName\": \"ADMIN\" }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Role created successfully",
                            content = @Content(schema = @Schema(implementation = Role.class))
                    )
            }
    )
    @PreAuthorize("hasPermission(-1, 'role', 'write')")
    @PostMapping("")
    public ResponseEntity<Role> createRole(@Valid @RequestBody RoleCreateRequestApi request) {
        var role = roleManager.createRole(request.getRoleName());
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve all roles",
            description = "Fetches a list of all roles available in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Roles retrieved successfully",
                            content = @Content(schema = @Schema(implementation = RoleGetResponseApi.class))
                    )
            }
    )
    @PreAuthorize("hasPermission(-1, 'role', 'read')")
    @GetMapping("")
    public ResponseEntity<RoleGetResponseApi> getAllRoles() {
        var roles = roleManager.getRoles();
        return new ResponseEntity<>(new RoleGetResponseApi(roles), HttpStatus.OK);
    }

    @Operation(
            summary = "Assign permissions to a role",
            description = "Adds permissions to a specific role. User require write:role:{roleId} access",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of permission IDs to be assigned",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"permissionIds\": [1, 2, 3] }"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Permissions assigned successfully"
                    )
            }
    )
    @PreAuthorize("hasPermission(#roleId, 'role', 'write')")
    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<Object> addPermissions(@PathVariable("roleId") Long roleId, @Valid @RequestBody PermissionAssignmentRequestApi request) {
        roleManager.addPermissionsToRole(roleId, request.getPermissionIds());
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
