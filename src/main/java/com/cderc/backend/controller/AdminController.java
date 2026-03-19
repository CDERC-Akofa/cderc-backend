package com.cderc.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Operation(summary = "Admin dashboard", security = @SecurityRequirement(name = "BearerAuth"))
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin dashboard";
    }
}
