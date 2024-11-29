package io.dealsplus.authsystem.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationConfig {

    @Value("${application.jwt.access.expiry}")
    private Long jwtAccessExpiryInMinutes;

    @Value("${application.jwt.refresh.expiry}")
    private Long jwtRefreshExpiryInMinutes;

    @Value("${application.jwt.secret}")
    private String jwtSecret;
}
