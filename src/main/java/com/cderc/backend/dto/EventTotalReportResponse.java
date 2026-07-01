package com.cderc.backend.dto;

import java.math.BigDecimal;

public class EventTotalReportResponse {
    private Long eventId;
    private String eventTitle;
    private BigDecimal totalExpenses;

    public EventTotalReportResponse(Long eventId, String eventTitle, BigDecimal totalExpenses) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.totalExpenses = totalExpenses;
    }

    public Long getEventId() { return eventId; }
    public String getEventTitle() { return eventTitle; }
    public BigDecimal getTotalExpenses() { return totalExpenses; }
}
