package com.cderc.backend.dto;

import com.cderc.backend.model.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseRequest {
    private String title;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private String description;

    public String getTitle() {
        return title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
