package com.cderc.backend.service;

import com.cderc.backend.dto.ChildExpenseSummaryResponse;
import com.cderc.backend.dto.ExpenseRequest;
import com.cderc.backend.mapper.ExpenseMapper;
import com.cderc.backend.model.Child;
import com.cderc.backend.model.Expense;
import com.cderc.backend.model.Organization;
import com.cderc.backend.model.User;
import com.cderc.backend.repository.ChildRepository;
import com.cderc.backend.repository.ExpenseRepository;
import com.cderc.backend.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ChildRepository childRepository;
    @Autowired
    public ChildService childService;
    public ExpenseService(ExpenseRepository expenseRepository, ChildRepository childRepository) {

        this.expenseRepository = expenseRepository;
        this.childRepository  = childRepository;
    }

    public Expense createExpense(Long childId, ExpenseRequest request, Organization organization){
        log.info("Creating expense for child {}", childId);

        Child child = childRepository.findByIdAndOrganizationId(
                childId,
                organization.getId()
        ).orElseThrow(() -> new RuntimeException("Child not found"));

        log.info("Expense amount: {}", request.getAmount());

        Expense savedExpense = ExpenseMapper.toEntity(request);
        savedExpense.setChild(child);
        savedExpense.setOrganization(organization);
        log.info("Expense saved with id {}", savedExpense.getId());
        return expenseRepository.save(savedExpense);
    }

    public List<Expense> findByChildAndOrganization(Long childId, Organization organization){
        return expenseRepository.findByChildIdAndOrganizationId(childId, organization.getId());
    }

    public Expense findByIdAndChildAndOrganization(Long expenseId,Long childId, Organization organization){
        return expenseRepository.findByIdAndChildIdAndOrganizationId(expenseId,childId, organization.getId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

    }

    public Optional<Expense>findById(Child child){
        return expenseRepository.findById(child.getId());
    }

    public Expense updateExpense(Long expenseId,Long childId, ExpenseRequest request, Organization organization){

        Expense expense = findByIdAndChildAndOrganization(
                expenseId,
                childId,
                organization
        );

        ExpenseMapper.updateEntity(expense, request); // ist das Nötigt

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId, Long childId,Organization organization ){

        Expense expense = findByIdAndChildAndOrganization(
                expenseId,
                childId,
                organization
        );
        expenseRepository.delete(expense);
    }

    public ChildExpenseSummaryResponse getTotalExpensesForChild(Long childId,
                                                                Long organizationId) {

        Child child = childRepository.findByIdAndOrganizationId(
                childId,
                organizationId
        ).orElseThrow(() -> new RuntimeException("Child not found"));

        BigDecimal total = expenseRepository.sumByChildAndOrganization(
                childId,
                organizationId
        );
        log.info("Expense summary for child {}", childId + "-" + total);
        return new ChildExpenseSummaryResponse(
                child.getId(),
                child.getName(),
                total
        );
    }
}
