package com.cderc.backend.controller;

import com.cderc.backend.dto.OrganizationResponse;
import com.cderc.backend.mapper.OrganizationMapper;
import com.cderc.backend.model.Organization;
import com.cderc.backend.service.OrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Organisation",
        description = "Organization anlegen von einem Suepradmin"
)
@RestController
@RequestMapping("/api/organizations")
@CrossOrigin
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public OrganizationResponse createOrganization(@RequestBody Organization organization) {
        Organization Createdorganization =  organizationService.createOrganization(organization);

        return OrganizationMapper.toResponse(Createdorganization);
    }
}
