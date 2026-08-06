package com.cderc.backend.controller;

import com.cderc.backend.dto.UserResponse;
import com.cderc.backend.mapper.ChildMapper;
import com.cderc.backend.mapper.UserMapper;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.User;
import com.cderc.backend.security.CustomUserDetails;
import com.cderc.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Users",
        description = "Benutzerverwaltung"
)
@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{organizationId}")
    public List<UserResponse> getUsersByOrganization(@PathVariable Long organizationId) {
        return userService.getAllByOrganization(organizationId).stream().map(UserMapper::toResponse)
                .toList();
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return user.getName() + " - " + user.getRole();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();

        return userService.findAll().stream()
                .filter(u -> u.getOrganization().getId().equals(org.getId()))
                .map(UserMapper::toResponse)
                .toList();

    }

    // Nur ADMIN kann User erstellen
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(@RequestBody User user, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Organization org = userDetails.getUser().getOrganization();

        user.setOrganization(org); // User wird automatisch der eigenen Organisation zugeordnet
        User newUser = userService.createUser(user);

        return UserMapper.toResponse(newUser);
    }

    @GetMapping("/api/user/me")
    public String me(Authentication authentication) {
        return "name=" + authentication.getName() + ", authorities=" + authentication.getAuthorities();
    }
}
