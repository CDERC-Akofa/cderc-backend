package com.cderc.backend.controller;


import com.cderc.backend.dto.OrganizationResponse;
import com.cderc.backend.mapper.OrganizationMapper;
import com.cderc.backend.model.Organization;
import com.cderc.backend.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(
        name = "Organisation",
        description = "Organization Verwaltung von einem Superadmin"
)
@RestController
@RequestMapping("/api/super-admin/organizations")
public class SuperAdminOrganizationController {
    private final OrganizationService organizationService;

    public SuperAdminOrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public OrganizationResponse createOrganization(@RequestBody Organization organization) {
        Organization organ = organizationService.createOrganization(organization);
        return OrganizationMapper.toResponse(organ);
    }

    @GetMapping
    public List<OrganizationResponse> getAllOrganizations() {

        return organizationService.findAll()
                .stream()
                .map(OrganizationMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public OrganizationResponse getOrganizationById(@PathVariable Long id) {

        Organization organ = organizationService.findById(id);
        return OrganizationMapper.toResponse(organ);
    }

    @PutMapping("/{id}")
    public OrganizationResponse  updateOrganization(@PathVariable Long id,
                                           @RequestBody Organization organization) {
        Organization organ =organizationService.update(id, organization);

        return OrganizationMapper.toResponse(organ);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganization(@PathVariable Long id) {
        organizationService.delete(id);
    }
}
