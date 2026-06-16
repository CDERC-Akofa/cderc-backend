package com.cderc.backend.controller;


import com.cderc.backend.model.Organization;
import com.cderc.backend.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin/organizations")
public class SuperAdminOrganizationController {
    private final OrganizationService organizationService;

    public SuperAdminOrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.findAll();
    }

    @GetMapping("/{id}")
    public Organization getOrganizationById(@PathVariable Long id) {
        return organizationService.findById(id);
    }

    @PutMapping("/{id}")
    public Organization updateOrganization(@PathVariable Long id,
                                           @RequestBody Organization organization) {
        return organizationService.update(id, organization);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.delete(id);
    }
}
