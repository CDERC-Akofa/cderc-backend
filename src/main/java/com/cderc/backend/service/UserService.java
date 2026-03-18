package com.cderc.backend.service;

import com.cderc.backend.model.Organization;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.UserRepository;
import com.cderc.backend.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
}
