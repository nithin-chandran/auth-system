package io.dealsplus.authsystem.server.security;

import java.util.List;

public class AuthConfig {

    public static final List<String> EXCLUDED_PATHS = List.of("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html");
}