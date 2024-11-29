package io.dealsplus.authsystem.authentication.models;

import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
public class TokenAuthentication implements Authentication {

    private Collection<? extends GrantedAuthority> authorities;
    private final Long principal;
    private boolean authenticated = false;
    private final AccountAccess details;

    public TokenAuthentication(Collection<? extends GrantedAuthority> authorities, Long principal, boolean authenticated, AccountAccess details) {
        this.authorities = authorities;
        this.principal = principal;
        this.authenticated = authenticated;
        this.details = details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return List.of((GrantedAuthority) () -> "read:structure");
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return "Token";
    }
}
