package io.dealsplus.authsystem.authorization.models;

import io.dealsplus.authsystem.authorization.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleGetResponseApi {
    private List<Role> roles;
}
