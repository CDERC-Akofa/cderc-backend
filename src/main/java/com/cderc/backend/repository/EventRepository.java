package com.cderc.backend.repository;

import com.cderc.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository  extends JpaRepository<Event, Long> {

    List<Event> findByOrganizationIdOrderByEventDateDesc(Long organizationId);

    Optional<Event> findByIdAndOrganizationId(Long id, Long organizationId);
}

