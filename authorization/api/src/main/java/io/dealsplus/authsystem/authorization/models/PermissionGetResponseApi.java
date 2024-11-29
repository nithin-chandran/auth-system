package io.dealsplus.authsystem.authorization.models;

import io.dealsplus.authsystem.authorization.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionGetResponseApi {
    private List<Permission> permissions;
}
