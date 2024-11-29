package io.dealsplus.authsystem.authorization.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long id;
    private String name;
    private List<Long> permissionIds;

    public Role(String name) {
        this.name = name;
    }
}
