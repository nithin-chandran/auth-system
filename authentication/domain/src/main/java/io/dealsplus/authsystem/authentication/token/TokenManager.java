package io.dealsplus.authsystem.authentication.token;

import io.dealsplus.authsystem.authentication.entity.Account;
import io.dealsplus.authsystem.authentication.models.AccountAccess;
import io.dealsplus.authsystem.authentication.token.models.Token;

public interface TokenManager {
    Token generateToken(Account account);
    //Token generateToken(String refreshToken);
    AccountAccess validateAccessToken(String accessToken);

}
