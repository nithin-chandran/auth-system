package io.dealsplus.authsystem.authentication.login.builder;

import io.dealsplus.authsystem.authentication.login.models.LoginRequest;
import io.dealsplus.authsystem.authentication.login.models.PasswordLoginCredentials;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordLoginRequestBuilder implements LoginRequestBuilder<PasswordLoginCredentials> {

    @Override
    public PasswordLoginCredentials build(LoginRequest request) {
        var username = "";
        if (!StringUtils.isEmpty(request.getPhone())) {
            username = request.getPhone();
        } else {
            username = request.getEmail();
        }
        return new PasswordLoginCredentials(username, request.getPassword());
    }
}
