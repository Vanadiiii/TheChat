package me.imatveev.thechat.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.Value;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * stupid controller just for springdoc)
 */
@RestController
@PreAuthorize("permitAll()")
public class LoginController {
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Operation(summary = "basic login for getting Bearer token")
    public Object login(@RequestBody LoginRequest request) {
        throw new NotImplementedException("Ooops)");
    }

    @Value
    private static class LoginRequest {
        @NonNull
        String phone;
        String password;
    }
}
