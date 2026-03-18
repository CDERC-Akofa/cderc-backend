package com.cderc.backend.service;

import com.cderc.backend.model.Child;
import com.cderc.backend.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;

    public List<Child> getAllByOrganization(Long organizationId) {
        return childRepository.findAllByOrganizationId(organizationId);
    }

    public List<Child> findByOrganizationId(Long organizationId) {
        return childRepository.findByOrganizationId(organizationId);
    }

    public Child createChild(Child child) {
        return childRepository.save(child);
    }
}
