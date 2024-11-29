package io.dealsplus.authsystem.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountToken {
    private Long id;
    private Long accountId;
    private String refreshToken;
    private String accessToken;
    private Date expiresAt;

}
