package io.dealsplus.authsystem.authorization;

import io.dealsplus.authsystem.authorization.entity.Role;
import io.dealsplus.authsystem.authorization.models.PermissionAssignmentRequestApi;
import io.dealsplus.authsystem.authorization.models.RoleAssignmentRequestApi;
import io.dealsplus.authsystem.authorization.models.RoleCreateRequestApi;
import io.dealsplus.authsystem.authorization.models.RoleGetResponseApi;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleApiIntegrationTest extends BaseIntegrationTest {
    @Test
    public void createRole_success() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createRoleInvalidAccess_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken + "invalid")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createRoleValidation_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createRoleInsufficientPermission_fail() throws Exception {

        String accessTokenWithNoPermission = getNoPermissionUserLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessTokenWithNoPermission)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createRoleDuplicate_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void getRoles_success() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());

        var result = mockMvc.perform(get("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isOk())
                .andReturn();

        var getResponse = objectMapper
                .readValue(result.getResponse().getContentAsString(), RoleGetResponseApi.class);
        assertEquals("dev", getResponse.getRoles().get(1).getName() , "values should be equal");
    }

    @Test
    public void getRolesInsufficientPermission_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());

        String newToken = getNoPermissionUserLoggedInToken();

        mockMvc.perform(get("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + newToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void addPermissionToRole_success() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        var result = mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var role = objectMapper
                .readValue(result.getResponse().getContentAsString(), Role.class);

        var request = new PermissionAssignmentRequestApi(
                new ArrayList<>(List.of(1L))
        );
        mockMvc.perform(post(String.format("/api/roles/%d/permissions", role.getId()))
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void addPermissionToRoleValidation_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        var result = mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var role = objectMapper
                .readValue(result.getResponse().getContentAsString(), Role.class);

        var request = new PermissionAssignmentRequestApi(
                new ArrayList<>(List.of())
        );
        mockMvc.perform(post(String.format("/api/roles/%d/permissions", role.getId()))
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addPermissionToRoleNotExists_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var request = new PermissionAssignmentRequestApi(
                new ArrayList<>(List.of(1L))
        );
        mockMvc.perform(post(String.format("/api/roles/%d/permissions", 91000990))
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void assignAccountToRole_success() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        var result = mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var role = objectMapper
                .readValue(result.getResponse().getContentAsString(), Role.class);

        var roleAssignmentRequest = new RoleAssignmentRequestApi(new ArrayList<Long>(List.of(role.getId())));

        mockMvc.perform(post("/api/accounts/1/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(roleAssignmentRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void assignAccountToRoleInsufficientAccess_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();
        String normalToken = getNoPermissionUserLoggedInToken();

        var apiRequest = new RoleCreateRequestApi("dev");

        var result = mockMvc.perform(post("/api/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var role = objectMapper
                .readValue(result.getResponse().getContentAsString(), Role.class);

        var roleAssignmentRequest = new RoleAssignmentRequestApi(new ArrayList<Long>(List.of(role.getId())));

        mockMvc.perform(post("/api/accounts/1/roles")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + normalToken)
                        .content(objectMapper.writeValueAsString(roleAssignmentRequest)))
                .andExpect(status().isForbidden());
    }
}
