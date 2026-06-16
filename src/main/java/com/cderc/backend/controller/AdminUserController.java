package com.cderc.backend.controller;
import com.cderc.backend.dto.CreateUserRequest;
import com.cderc.backend.model.User;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody CreateUserRequest request,
                           Authentication authentication) {
        return userService.createUserByAdmin(request, authentication);
    }
}
