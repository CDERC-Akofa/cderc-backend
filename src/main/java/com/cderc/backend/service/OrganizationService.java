package com.cderc.backend.service;

import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }
}
