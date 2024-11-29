package io.dealsplus.authsystem.authentication.registration;

import io.dealsplus.authsystem.authentication.registration.models.RegistrationRequest;
import io.dealsplus.authsystem.authentication.registration.models.RegistrationResponse;

public interface RegisterAccount {
    RegistrationResponse register(RegistrationRequest request);
}
