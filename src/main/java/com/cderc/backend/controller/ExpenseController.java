package com.cderc.backend.controller;


import com.cderc.backend.dto.*;
import com.cderc.backend.mapper.ExpenseMapper;
import com.cderc.backend.mapper.MemberMapper;
import com.cderc.backend.model.Child;
import com.cderc.backend.model.Expense;
import com.cderc.backend.model.Member;
import com.cderc.backend.model.User;
import com.cderc.backend.service.ExpenseService;
import com.cderc.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children/{childId}/expenses")
@CrossOrigin
public class ExpenseController {
    @Autowired
    public ExpenseService expenseService;
    @Autowired
    public UserService userService;

    //
//    POST
//    POST /api/children/1/expenses
    @PostMapping
    public ExpenseResponse createExpense(@PathVariable Long childId, @RequestBody ExpenseRequest request, Authentication authentication) {
        System.out.println("*******CREATE EXPENSE 1 *******" + childId);

        User user = userService.findByEmail(authentication.getName());
        Expense savedExpense = expenseService.createExpense(childId,
                request,
                user.getOrganization());

        System.out.println("*******CREATE EXPENSE 2 *******");
        return ExpenseMapper.toResponse(savedExpense);
    }

    //    GET /api/children/1/expenses
    @GetMapping
    public List<ExpenseResponse> getExpensesByChild(@PathVariable Long childId,
                                                    Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());


        return expenseService.findByChildAndOrganization(childId, user.getOrganization()).stream().map(ExpenseMapper::toResponse)
                .toList();
    }

    @GetMapping("/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable Long childId,
                                          @PathVariable Long expenseId,
                                          Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Expense expense = expenseService.findByIdAndChildAndOrganization(
                expenseId,
                childId,
                user.getOrganization()
        );
        return ExpenseMapper.toResponse(expense);
    }

    @PutMapping("/{expenseId}")
    public ExpenseResponse updateExpense(@PathVariable Long childId,
                                         @PathVariable Long expenseId,
                                         @RequestBody ExpenseRequest request,
                                         Authentication authentication) {

        User user = userService.findByEmail(authentication.getName());

        Expense updatedExpense = expenseService.updateExpense(
                expenseId,
                childId,
                request,
                user.getOrganization()
        );

        return ExpenseMapper.toResponse(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable Long childId,
                              @PathVariable Long expenseId,
                              Authentication authentication) {

        User user = userService.findByEmail(authentication.getName());

        expenseService.deleteExpense(
                expenseId,
                childId,
                user.getOrganization()
        );
    }
//    TOTAL
//    GET /api/children/1/expenses/total

    @GetMapping("/total")
    public ChildExpenseSummaryResponse getTotalExpensesForChild(
            @PathVariable Long childId,
            Authentication authentication) {

        User user = userService.findByEmail(authentication.getName());

        return expenseService.getTotalExpensesForChild(
                childId,
                user.getOrganization().getId()
        );
    }
}