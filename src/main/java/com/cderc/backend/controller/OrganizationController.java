package com.cderc.backend.controller;

import com.cderc.backend.model.Organization;
import com.cderc.backend.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@CrossOrigin
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }
}
