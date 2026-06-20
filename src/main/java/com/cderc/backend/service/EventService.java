package com.cderc.backend.service;

import com.cderc.backend.dto.EventRequest;
import com.cderc.backend.mapper.EventMapper;
import com.cderc.backend.model.Event;
import com.cderc.backend.model.Organization;
import com.cderc.backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(EventRequest request, Organization organization) {
        Event event = EventMapper.toEntity(request);
        event.setOrganization(organization);
        return eventRepository.save(event);
    }

    public List<Event> findAllByOrganization(Long organizationId) {
        return eventRepository.findByOrganizationIdOrderByEventDateDesc(organizationId);
    }

    public Event findByIdAndOrganization(Long id, Long organizationId) {
        return eventRepository.findByIdAndOrganizationId(id, organizationId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public Event update(Long id, Long organizationId, EventRequest request) {
        Event event = findByIdAndOrganization(id, organizationId);
        EventMapper.updateEntity(event, request);
        return eventRepository.save(event);
    }

    public void delete(Long id, Long organizationId) {
        Event event = findByIdAndOrganization(id, organizationId);
        eventRepository.delete(event);
    }
}
