package com.cderc.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
//    @Operation(summary = "Admin dashboard", security = @SecurityRequirement(name = "BearerAuth"))
//    @GetMapping("/dashboard")
//
//    public String dashboard() {
//        return "Admin dashboard";
//    }

    @Operation(summary = "Admin Dashboard", security = @SecurityRequirement(name = "BearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/dashboard")
    public String adminDashboard() {
        return "Admin dashboard";
    }

    @Operation(summary = "User Info", security = @SecurityRequirement(name = "BearerAuth"))
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/user/info")
    public String userInfo() {
        return "User info";
    }
}
