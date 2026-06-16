package com.cderc.backend.service;

import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization createOrganization(Organization organization) {
        if (organizationRepository.existsByName(organization.getName())) {
            throw new RuntimeException("Organization already exists");
        }
        return organizationRepository.save(organization);
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Organization findById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    public Organization update(Long id, Organization updated) {
        Organization org = findById(id);
        org.setName(updated.getName());
        org.setEmail(updated.getEmail());
        org.setThemeColor(updated.getThemeColor());
        org.setLogo(updated.getLogo());
        return organizationRepository.save(org);
    }

    public void delete(Long id) {
        Organization org = findById(id);
        organizationRepository.delete(org);
    }
}
