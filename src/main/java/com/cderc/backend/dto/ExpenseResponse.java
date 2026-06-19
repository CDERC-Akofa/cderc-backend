package com.cderc.backend.dto;

import com.cderc.backend.model.ExpenseCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
public class ExpenseResponse {

    private Long id;
    private String title;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private String description;

    private Long childId;
    private String childName;

    public ExpenseResponse(Long id, String title, BigDecimal amount, LocalDate expenseDate, ExpenseCategory category, String description,Long childId, String name) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.description = description;
        this.childId = childId;
        this.childName = name;
    }

    public Long getId() {
        return id;
    }

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
public String getDescription(){
        return  description;
}
    public Long getChildId() {
        return childId;
    }

    public String getChildName() {
        return childName;
    }
}
