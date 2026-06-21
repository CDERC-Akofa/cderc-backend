package com.cderc.backend.dto;

import com.cderc.backend.model.EventExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventExpenseResponse {
    private Long id;
    private String title;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private EventExpenseCategory category;
    private String description;
    private Long eventId;
    private String eventTitle;

    public EventExpenseResponse(Long id, String title, BigDecimal amount,
                                LocalDate expenseDate, EventExpenseCategory category,
                                String description, Long eventId, String eventTitle) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.description = description;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public EventExpenseCategory getCategory() { return category; }
    public String getDescription() { return description; }
    public Long getEventId() { return eventId; }
    public String getEventTitle() { return eventTitle; }
}
