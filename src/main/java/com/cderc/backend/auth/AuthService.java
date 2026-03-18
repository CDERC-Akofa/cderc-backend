package com.cderc.backend.auth;

import com.cderc.backend.dto.AuthResponse;
import com.cderc.backend.dto.LoginRequest;
import com.cderc.backend.dto.RegisterRequest;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.OrganizationRepository;
import com.cderc.backend.repository.UserRepository;
import com.cderc.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       OrganizationRepository organizationRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {


        System.out.println("SERVICE REGISTER");
//        Organization org = organizationRepository.findById(request.getOrganizationId())
//                .orElseThrow();
//
       User user = new User();
//        user.setName(request.getName());
       user.setEmail(request.getEmail());
       user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRole(request.getRole());
//        user.setOrganization(org);
//
       userRepository.save(user);
//
//        String token = jwtService.generateToken(user.getEmail());
//
//        return new AuthResponse(token);

        return new AuthResponse("saved");
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
