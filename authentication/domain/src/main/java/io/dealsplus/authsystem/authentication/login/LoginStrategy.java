package io.dealsplus.authsystem.authentication.login;

import io.dealsplus.authsystem.authentication.login.models.LoginCredentials;
import io.dealsplus.authsystem.authentication.token.models.Token;

public interface LoginStrategy<T extends LoginCredentials> {
    Token authenticate(T credentials);
}
