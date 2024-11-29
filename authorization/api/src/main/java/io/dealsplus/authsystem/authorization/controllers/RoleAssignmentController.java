package io.dealsplus.authsystem.authorization.controllers;

import io.dealsplus.authsystem.authorization.account.AccountManager;
import io.dealsplus.authsystem.authorization.models.RoleAssignmentRequestApi;
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

@RestController
@RequestMapping("/api/accounts")
@Validated
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Role Assignment", description = "Handles assigning of roles to accounts")
public class RoleAssignmentController {

    private final AccountManager accountManager;

    public RoleAssignmentController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Operation(
            summary = "Assign multiple roles to an account",
            description = """
                    This endpoint allows assigning multiple roles to a specified account.
                    The user need write:role:* access to use the API.
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of role IDs to assign",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RoleAssignmentRequestApi.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "roleIds": [101, 102, 103]
                                            }
                                            """)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles assigned successfully"),
    })
    @PreAuthorize("hasPermission(-1, 'role', 'write')")
    @PostMapping("/{accountId}/roles")
    public ResponseEntity<Object> assignRolesToAccount(@PathVariable("accountId") Long accountId, @Valid @RequestBody RoleAssignmentRequestApi request) {
        accountManager.addRoles(accountId, request.getRoleIds());
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
