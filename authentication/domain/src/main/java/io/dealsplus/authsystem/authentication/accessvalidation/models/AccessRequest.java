package io.dealsplus.authsystem.authentication.accessvalidation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequest {
    private String accessToken;
}
