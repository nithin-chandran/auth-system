package io.dealsplus.authsystem.authorization;

import io.dealsplus.authsystem.authorization.models.PermissionCreateRequestApi;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PermissionApiIntegrationTest extends BaseIntegrationTest {

    @Test
    public void createPermission_success() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new PermissionCreateRequestApi("read", "account", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createPermissionWithResourceId_success() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new PermissionCreateRequestApi("read", "account", false, new ArrayList<>(List.of(1L, 2L)));

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createPermissionInvalidToken_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new PermissionCreateRequestApi("read", "account", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken + "invalidToken")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createPermissionValidation_fail() throws Exception {

        String accessToken = getAdminLoggedInToken();

        var apiRequest = new PermissionCreateRequestApi("read", "invalidResource", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());

        apiRequest = new PermissionCreateRequestApi("invalidAction", "account", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());

        apiRequest = new PermissionCreateRequestApi("", "account", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPermissionInsufficientPermission_fail() throws Exception {

        String accessTokenWithNoPermission = getNoPermissionUserLoggedInToken();

        var apiRequest = new PermissionCreateRequestApi("read", "account", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessTokenWithNoPermission)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createPermissionExist_fail() throws Exception {

        String accessTokenWithNoPermission = getAdminLoggedInToken();

        var apiRequest = new PermissionCreateRequestApi("read", "account", true, new ArrayList<>());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessTokenWithNoPermission)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/permissions")
                        .contentType("application/json")
                        .header("authorization", "Bearer " + accessTokenWithNoPermission)
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isPreconditionFailed());
    }


}
