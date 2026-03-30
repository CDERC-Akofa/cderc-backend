package com.cderc.backend.service;

import com.cderc.backend.model.Child;
import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.ChildRepository;
import com.cderc.backend.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {

    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private OrganizationRepository  orgRepository;

    @Autowired
    private OrganizationService orgService;

    public List<Child> getAllByOrganization(Long organizationId) {
        return childRepository.findAllByOrganizationId(organizationId);
    }


    public Child createChild(Child child) {
        return childRepository.save(child);
    }

    public List<Child> findByOrganizationId(Long organizationId) {
        return childRepository.findAllByOrganizationId(organizationId);
    }

    public Child findByIdAndOrganizationId(Long id, Long organizationId) {
        return childRepository.findByIdAndOrganizationId(id, organizationId);
          //      .orElseThrow(() -> new RuntimeException("Child not found"));
    }

    public Child updateChild(Long id, Long organizationId, Child updatedChild) {
        Child existingChild = childRepository.findByIdAndOrganizationId(id, organizationId);
           //     .orElseThrow(() -> new RuntimeException("Child not found"));

        existingChild.setName(updatedChild.getName());
        existingChild.setBirthDate(updatedChild.getBirthDate());
        existingChild.setGender(updatedChild.getGender());
        existingChild.setHealthStatus(updatedChild.getHealthStatus());
        existingChild.setSchoolStatus(updatedChild.getSchoolStatus());

          //  existingChild.setOrganization();

        return childRepository.save(existingChild);
    }

    public void deleteChild(Long id, Long organizationId) {
        Child child = childRepository.findByIdAndOrganizationId(id, organizationId);
      //          .orElseThrow(() -> new RuntimeException("Child not found"));

        childRepository.delete(child);
    }
}
