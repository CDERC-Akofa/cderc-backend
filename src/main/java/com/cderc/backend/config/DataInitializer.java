package com.cderc.backend.config;

import com.cderc.backend.model.Role;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("superadmin@cderc.org").isEmpty()) {
            User superAdmin = new User();
            superAdmin.setEmail("superadmin@cderc.org");
            superAdmin.setPassword(passwordEncoder.encode("ChangeMe123!"));
            superAdmin.setRole(Role.SUPER_ADMIN);

            userRepository.save(superAdmin);
        }
    }
}
