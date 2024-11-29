package io.dealsplus.authsystem.server;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@ComponentScan(basePackages = {
        "io.dealsplus.authsystem",
})
public class ApiRoutingConfiguration {
}
