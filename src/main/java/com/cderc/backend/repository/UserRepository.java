package com.cderc.backend.repository;

import com.cderc.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrganizationId(Long organizationId);
    Optional<User> findByEmail(String email);
}
