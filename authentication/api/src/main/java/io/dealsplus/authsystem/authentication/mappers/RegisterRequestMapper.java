package io.dealsplus.authsystem.authentication.mappers;

import io.dealsplus.authsystem.authentication.models.RegistrationRequestApi;
import io.dealsplus.authsystem.authentication.registration.models.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterRequestMapper {
    public RegistrationRequest toDomainRequest(RegistrationRequestApi apiRequest) {
        return new RegistrationRequest(
                apiRequest.getEmail(), apiRequest.getPassword(), apiRequest.getPhone()
        );
    }
}
