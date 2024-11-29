package io.dealsplus.authsystem.authentication.login.builder;

import io.dealsplus.authsystem.authentication.login.models.LoginCredentials;
import io.dealsplus.authsystem.authentication.login.models.LoginRequest;

public interface LoginRequestBuilder<R extends LoginCredentials> {
    R build(LoginRequest request);
}
