package com.cderc.backend.dto;

import java.time.LocalDate;

public class EventRequest {
    private String title;
    private LocalDate eventDate;
    private String location;
    private String description;

    public String getTitle() { return title; }
    public LocalDate getEventDate() { return eventDate; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
}
