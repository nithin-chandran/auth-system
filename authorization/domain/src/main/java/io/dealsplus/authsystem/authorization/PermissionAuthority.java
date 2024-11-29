package io.dealsplus.authsystem.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionAuthority implements GrantedAuthority {
    private String permission;
    @Override
    public String getAuthority() {
        return permission;
    }
}
