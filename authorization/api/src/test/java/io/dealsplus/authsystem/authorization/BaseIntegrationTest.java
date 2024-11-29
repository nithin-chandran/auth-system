package io.dealsplus.authsystem.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dealsplus.authsystem.authentication.login.models.LoginResponse;
import io.dealsplus.authsystem.authentication.models.LoginRequestApi;
import io.dealsplus.authsystem.authentication.models.RegistrationRequestApi;
import io.dealsplus.authsystem.server.Application;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

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

    protected String getAdminLoggedInToken() throws Exception {
        var loginRequest = new LoginRequestApi("initial_password", "test@example.com", null);

        var result = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();
        var loginResponse = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                LoginResponse.class);
        return loginResponse.getAccessToken();
    }

    protected String getNoPermissionUserLoggedInToken() throws Exception {

        var apiRequest = new RegistrationRequestApi("password", "test@valid.com", "9099000900");

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(apiRequest)))
                .andExpect(status().isCreated());

        var loginRequest = new LoginRequestApi("password", "test@valid.com", null);

        var result = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();
        var loginResponse = objectMapper.readValue(result
                        .getResponse()
                        .getContentAsString(),
                LoginResponse.class);
        return loginResponse.getAccessToken();
    }
}
