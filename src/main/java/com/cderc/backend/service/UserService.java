package com.cderc.backend.service;

import com.cderc.backend.dto.CreateUserRequest;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.Role;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.UserRepository;
import com.cderc.backend.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public List<User> getAllByOrganization(Long organizationId) {
        return userRepository.findAllByOrganizationId(organizationId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Optional: Update, Delete Skeletons später hinzufügen

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id, Authentication authentication) {
       CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();

        User user =  userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOrganization().getId().equals(org.getId())) {
            throw new RuntimeException("Cannot delete user from another organization");
        }

        userRepository.delete(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User createUserByAdmin(CreateUserRequest request, Authentication authentication) {

        String adminEmail = authentication.getName();
        User admin = findByEmail(adminEmail);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (request.getRole() == Role.SUPER_ADMIN || request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot create SUPER_ADMIN or ADMIN");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setOrganization(admin.getOrganization());

        return userRepository.save(user);
    }
}
