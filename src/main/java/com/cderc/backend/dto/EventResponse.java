package com.cderc.backend.dto;

import java.time.LocalDate;

public class EventResponse {
    private Long id;
    private String title;
    private LocalDate eventDate;
    private String location;
    private String description;
    private Long organizationId;

    public EventResponse(Long id, String title, LocalDate eventDate,
                         String location, String description, Long organizationId) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.location = location;
        this.description = description;
        this.organizationId = organizationId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public LocalDate getEventDate() { return eventDate; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public Long getOrganizationId() { return organizationId; }
}
