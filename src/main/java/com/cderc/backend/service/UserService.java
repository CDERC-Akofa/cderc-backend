package com.cderc.backend.service;

import com.cderc.backend.dto.CreateUserRequest;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.Role;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.UserRepository;
import com.cderc.backend.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public List<User> getAllByOrganization(Long organizationId) {
        return userRepository.findAllByOrganizationId(organizationId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id, Authentication authentication) {
       CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();

        User user =  userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOrganization() == null || !user.getOrganization().getId().equals(org.getId())) {
            throw new RuntimeException("Cannot delete user from another organization");
        }
        if (user.getRole() == Role.SUPER_ADMIN || user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot delete SUPER_ADMIN or ADMIN");
        }

        userRepository.delete(user);
    }

    @Transactional
    public User update(Long id, CreateUserRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();
        String email = normalizeEmail(request.getEmail());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOrganization() == null || !user.getOrganization().getId().equals(org.getId())) {
            throw new RuntimeException("Cannot update user from another organization");
        }
        if (request.getRole() == Role.SUPER_ADMIN || request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot update user to SUPER_ADMIN or ADMIN");
        }
        if (user.getRole() == Role.SUPER_ADMIN || user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot update SUPER_ADMIN or ADMIN");
        }
        if (userRepository.existsByEmailAndIdNot(email, id)) {
            throw new RuntimeException("Email already exists");
        }

        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setRole(request.getRole());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User createUserByAdmin(CreateUserRequest request, Authentication authentication) {
        String adminEmail = authentication.getName();
        User admin = findByEmail(adminEmail);
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        if (request.getRole() == Role.SUPER_ADMIN || request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot create SUPER_ADMIN or ADMIN");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setOrganization(admin.getOrganization());

        return userRepository.save(user);
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}
