package com.cderc.backend.controller;

import com.cderc.backend.model.Organization;
import com.cderc.backend.model.User;
import com.cderc.backend.security.CustomUserDetails;
import com.cderc.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{organizationId}")
    public List<User> getUsersByOrganization(@PathVariable Long organizationId) {
        return userService.getAllByOrganization(organizationId);
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return user.getName() + " - " + user.getRole();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();

        // Alle User der eigenen Organisation zurückgeben
        return userService.findAll().stream()
                .filter(u -> u.getOrganization().getId().equals(org.getId()))
                .toList();
    }

    // Nur ADMIN kann User erstellen
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody User user, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();

        user.setOrganization(org); // User wird automatisch der eigenen Organisation zugeordnet
        return userService.createUser(user);
    }

}
