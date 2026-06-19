package com.cderc.backend.mapper;

import com.cderc.backend.dto.ExpenseRequest;
import com.cderc.backend.dto.ExpenseResponse;
import com.cderc.backend.model.Expense;

public class ExpenseMapper {
    public static Expense toEntity(ExpenseRequest request) {

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());

        return expense;
    }

    public static ExpenseResponse toResponse(Expense expense) {

        return new ExpenseResponse(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getChild().getId(),
                expense.getChild().getName()
        );
    }

    public static void updateEntity(Expense expense,
                                    ExpenseRequest request) {

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
    }
}
