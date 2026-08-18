package com.cderc.backend.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cderc.backend.dto.AuthResponse;
import com.cderc.backend.dto.LoginRequest;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.Role;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.OrganizationRepository;
import com.cderc.backend.repository.UserRepository;
import com.cderc.backend.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Test
    void loginNormalizesAdminEmailAndReturnsRoleClaims() {
        UserRepository userRepository = mock(UserRepository.class);
        OrganizationRepository organizationRepository = mock(OrganizationRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthService authService = new AuthService(
                userRepository,
                organizationRepository,
                passwordEncoder,
                new JwtService("test-secret-for-auth-service")
        );

        Organization organization = new Organization();
        organization.setId(2L);

        User admin = new User();
        admin.setId(7L);
        admin.setName("Togo Admin");
        admin.setEmail("admin@cderc.org");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRole(Role.ADMIN);
        admin.setOrganization(organization);

        when(userRepository.findByEmail("admin@cderc.org")).thenReturn(Optional.of(admin));

        LoginRequest request = new LoginRequest();
        request.setEmail("  Admin@CDERC.org ");
        request.setPassword("123456");

        AuthResponse response = authService.login(request);
        DecodedJWT jwt = JWT.decode(response.getToken());

        assertThat(response.getUser().getRole()).isEqualTo(Role.ADMIN);
        assertThat(response.getUser().getOrganizationId()).isEqualTo(2L);
        assertThat(jwt.getClaim("role").asString()).isEqualTo("ADMIN");
        assertThat(jwt.getClaim("organizationId").asLong()).isEqualTo(2L);
    }
}
