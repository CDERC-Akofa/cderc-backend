package com.cderc.backend.repository;

import com.cderc.backend.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findAllByOrganizationId(Long organizationId);
    Child findByIdAndOrganizationId(Long id, Long organizationId);
}
