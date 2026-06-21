package com.cderc.backend.dto;

import com.cderc.backend.model.EventExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventExpenseRequest {
    private String title;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private EventExpenseCategory category;
    private String description;

    public String getTitle() { return title; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public EventExpenseCategory getCategory() { return category; }
    public String getDescription() { return description; }

}
