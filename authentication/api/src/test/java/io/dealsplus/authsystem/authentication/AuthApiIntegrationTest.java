package io.dealsplus.authsystem.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dealsplus.authsystem.authentication.login.models.LoginResponse;
import io.dealsplus.authsystem.authentication.models.AccessRequestApi;
import io.dealsplus.authsystem.authentication.models.LoginRequestApi;
import io.dealsplus.authsystem.authentication.models.RegistrationRequestApi;
import io.dealsplus.authsystem.server.Application;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthApiIntegrationTest  {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static Flyway flyway;

    @BeforeEach
    void setup() {

        flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5433/test_db", "test_user", "test_password")
                .locations("classpath:db/migrations")
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();


    }

    @AfterAll
    static void cleanup() {
        flyway.clean();
    }

    @Test
    public void registerAccount_success() throws Exception {
        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void registerAccount_duplicateFail() throws Exception {
        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void registerAccount_validationFail() throws Exception {
        var apiRequest = new RegistrationRequestApi("password", "testvalid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("email: Email should be valid; "));

        apiRequest = new RegistrationRequestApi("password", "", "");

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("emailOrPhonePresent: Either email or phone must be provided; "));
    }

    @Test
    public void login_success() throws Exception {

        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)));

        var loginRequest = new LoginRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUserNotExist_fail() throws Exception {
        var loginRequest = new LoginRequestApi("password", "testNotPresent@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void loginUserValidation_fail() throws Exception {
        var loginRequest = new LoginRequestApi("", "testNotPresent@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void loginPasswordWrong_fail() throws Exception {
        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(apiRequest)));

        var loginRequest = new LoginRequestApi("password_wrong", "testNotPresent@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void validateToken_success() throws Exception {

        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(apiRequest)));

        var loginRequest = new LoginRequestApi("password", "test@valid.com", "9099000900");

        var result = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();
        var loginResponse = objectMapper.readValue(result
                .getResponse()
                .getContentAsString(),
                LoginResponse.class) ;

        mockMvc.perform(post("/api/auth/token/validate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new AccessRequestApi(loginResponse.getAccessToken()))))
                .andExpect(status().isOk());
    }

    @Test
    public void validateTokenInvalidAccess_fail() throws Exception {

        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(apiRequest)));

        var loginRequest = new LoginRequestApi("password", "test@valid.com", "9099000900");

        var result = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn();
        var loginResponse = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                LoginResponse.class) ;

        mockMvc.perform(post("/api/auth/token/validate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new AccessRequestApi(loginResponse.getAccessToken()+"a"))))
                .andExpect(status().isUnauthorized());
    }
}
