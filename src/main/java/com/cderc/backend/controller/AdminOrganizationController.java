package com.cderc.backend.controller;

import com.cderc.backend.dto.OrganizationResponse;
import com.cderc.backend.mapper.OrganizationMapper;
import com.cderc.backend.model.User;
import com.cderc.backend.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Admin organization",
        description = "Organisation des angemeldeten Admins"
)
@SecurityRequirement(name = "BearerAuth")
@RestController
@RequestMapping("/api/admin/organization")
public class AdminOrganizationController {
    private final UserService userService;

    public AdminOrganizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public OrganizationResponse getCurrentOrganization(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        if (user.getOrganization() == null) {
            throw new RuntimeException("Organization not found");
        }

        return OrganizationMapper.toResponse(user.getOrganization());
    }
}
