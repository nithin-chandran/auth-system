package io.dealsplus.authsystem.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableMethodSecurity
@EnableJpaRepositories(basePackages = "io.dealsplus.authsystem")
@EntityScan(basePackages = "io.dealsplus.authsystem")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
