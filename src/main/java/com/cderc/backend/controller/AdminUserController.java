package com.cderc.backend.controller;
import com.cderc.backend.dto.CreateUserRequest;
import com.cderc.backend.dto.UserResponse;
import com.cderc.backend.mapper.UserMapper;
import com.cderc.backend.model.User;
import com.cderc.backend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Users",
        description = "Benutzeranlegen"
)
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request,
                                   Authentication authentication) {
        User user = userService.createUserByAdmin(request, authentication);
        return UserMapper.toResponse(user);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @RequestBody CreateUserRequest request,
                                   Authentication authentication) {
        User user = userService.update(id, request, authentication);
        return UserMapper.toResponse(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id,
                           Authentication authentication) {
        userService.delete(id, authentication);
    }
}
