package com.cderc.backend.mapper;

import com.cderc.backend.dto.EventExpenseRequest;
import com.cderc.backend.dto.EventExpenseResponse;
import com.cderc.backend.model.EventExpense;

public class EventExpenseMapper {
    public static EventExpense toEntity(EventExpenseRequest request) {
        EventExpense expense = new EventExpense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        return expense;
    }

    public static EventExpenseResponse toResponse(EventExpense expense) {
        return new EventExpenseResponse(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getEvent().getId(),
                expense.getEvent().getTitle()
        );
    }
    public static void updateEntity(EventExpense expense, EventExpenseRequest request) {
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
    }
}
