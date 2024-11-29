package io.dealsplus.authsystem.authentication.filters;

import io.dealsplus.authsystem.authentication.accessvalidation.AccessManager;
import io.dealsplus.authsystem.authentication.models.AccountAccess;
import io.dealsplus.authsystem.authentication.models.TokenAuthentication;
import io.dealsplus.authsystem.authentication.token.exception.InvalidTokenException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class AccountAccessFilter extends OncePerRequestFilter {

    private final AccessManager accessManager;

    private final List<AntPathRequestMatcher> excludedMatchers;


    public AccountAccessFilter(AccessManager accessManager, List<AntPathRequestMatcher> excludedMatchers) {
        this.accessManager = accessManager;
        this.excludedMatchers = excludedMatchers;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getTokenFromRequest(request);
        if (StringUtils.isEmpty(accessToken)) {
            addUnauthorizedResponse(response);
            return;
        }
        try {
            AccountAccess accountAccess = accessManager.validateAccess(accessToken);

            SecurityContextHolder.getContext().setAuthentication(
                    new TokenAuthentication(
                            List.of(),
                            accountAccess.getId(),
                            true,
                            accountAccess
                    )
            );
        } catch (InvalidTokenException e) {
            addUnauthorizedResponse(response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return excludedMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));

    }

    private void addUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.getWriter().write("Authentication failed");
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Get token after "Bearer "
        }
        return null;
    }
}
