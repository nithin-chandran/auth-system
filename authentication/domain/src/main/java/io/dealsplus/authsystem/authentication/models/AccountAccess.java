package io.dealsplus.authsystem.authentication.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAccess {
    private Long id;
    private String email;
    private String phone;
}
