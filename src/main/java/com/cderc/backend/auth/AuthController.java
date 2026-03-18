package com.cderc.backend.auth;

import com.cderc.backend.dto.AuthResponse;
import com.cderc.backend.dto.LoginRequest;
import com.cderc.backend.dto.RegisterRequest;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.UserRepository;
import com.cderc.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
}
}


