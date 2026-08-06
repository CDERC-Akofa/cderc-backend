package com.cderc.backend.auth;

import com.cderc.backend.dto.AuthResponse;
import com.cderc.backend.dto.LoginRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Authentication",
        description = "Anmeldung ( Super admin, Admin, Benutzer ..."
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
}
}


