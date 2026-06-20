package com.cderc.backend.controller;

import com.cderc.backend.dto.EventRequest;
import com.cderc.backend.dto.EventResponse;
import com.cderc.backend.mapper.EventMapper;
import com.cderc.backend.model.Event;
import com.cderc.backend.model.User;
import com.cderc.backend.service.EventService;
import com.cderc.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    public EventController(EventService eventService,
                           UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping
    public EventResponse create(@RequestBody EventRequest request,
                                Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        Event saved = eventService.create(request, user.getOrganization());

        return EventMapper.toResponse(saved);
    }

    @GetMapping
    public List<EventResponse> findAll(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return eventService.findAllByOrganization(user.getOrganization().getId())
                .stream()
                .map(EventMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public EventResponse findById(@PathVariable Long id,
                                  Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        Event event = eventService.findByIdAndOrganization(
                id,
                user.getOrganization().getId()
        );

        return EventMapper.toResponse(event);
    }

    @PutMapping("/{id}")
    public EventResponse update(@PathVariable Long id,
                                @RequestBody EventRequest request,
                                Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        Event updated = eventService.update(
                id,
                user.getOrganization().getId(),
                request
        );

        return EventMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        eventService.delete(id, user.getOrganization().getId());
    }
}
