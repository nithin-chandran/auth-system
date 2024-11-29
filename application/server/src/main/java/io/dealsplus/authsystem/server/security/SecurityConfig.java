package io.dealsplus.authsystem.server.security;

import io.dealsplus.authsystem.authentication.accessvalidation.AccessManager;
import io.dealsplus.authsystem.authentication.filters.AccountAccessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity()
public class SecurityConfig {

    private final AccessManager accessManager;

    public SecurityConfig(AccessManager accessManager) {
        this.accessManager = accessManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<AntPathRequestMatcher> excludedMatchers = AuthConfig.EXCLUDED_PATHS
                .stream()
                .map(AntPathRequestMatcher::new)
                .toList();
        var accountAccessFilter = new AccountAccessFilter(accessManager, excludedMatchers);

        http.csrf(AbstractHttpConfigurer::disable);
        http.requestCache(RequestCacheConfigurer::disable);
        http.addFilterAfter(accountAccessFilter, BasicAuthenticationFilter.class);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(AuthConfig.EXCLUDED_PATHS.toArray(new String[0])).permitAll();
                    auth.anyRequest().authenticated();
                }
        );

        return http.build();
    }
}
