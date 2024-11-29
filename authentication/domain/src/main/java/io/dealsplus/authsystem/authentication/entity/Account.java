package io.dealsplus.authsystem.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private String email;
    private String phone;
    private String passwordHash;

    public Account(String email, String phone, String passwordHash) {
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
    }

//    public Account(Long id, String email, String phone, String passwordHash) {
//        this.email = email;
//        this.phone = phone;
//        this.passwordHash = passwordHash;
//        this.id = id;
//    }
}
