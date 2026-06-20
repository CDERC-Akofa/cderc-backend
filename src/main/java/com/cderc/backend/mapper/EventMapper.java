package com.cderc.backend.mapper;

import com.cderc.backend.dto.EventRequest;
import com.cderc.backend.dto.EventResponse;
import com.cderc.backend.model.Event;

public class EventMapper {
    public static Event toEntity(EventRequest request) {
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());
        event.setDescription(request.getDescription());
        return event;
    }

    public static EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getEventDate(),
                event.getLocation(),
                event.getDescription(),
                event.getOrganization().getId()
        );
    }

    public static void updateEntity(Event event, EventRequest request) {
        event.setTitle(request.getTitle());
        event.setEventDate(request.getEventDate());
        event.setLocation(request.getLocation());
        event.setDescription(request.getDescription());
    }
}
