package com.cderc.backend.config;


import com.cderc.backend.security.JwtAuthenticationFilter;
import com.cderc.backend.security.CustomUserDetailsService;
import com.cderc.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // frei für register/login
                        // ADMIN only
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // USER + ADMIN
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/children", "/api/children/**")
                                .hasAnyRole("ADMIN", "SOCIAL_WORKER")
                                .requestMatchers(HttpMethod.GET, "/api/children", "/api/children/**")
                                .hasAnyRole("USER", "ADMIN", "SOCIAL_WORKER")

                               .requestMatchers(HttpMethod.PUT, "/api/children", "/api/children/**")
                               .hasAnyRole("ADMIN", "SOCIAL_WORKER")
                               .requestMatchers(HttpMethod.DELETE, "/api/children", "/api/children/**")
                               .hasAnyRole("ADMIN", "SOCIAL_WORKER")

                        .anyRequest().authenticated() // alles andere geschützt
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}
