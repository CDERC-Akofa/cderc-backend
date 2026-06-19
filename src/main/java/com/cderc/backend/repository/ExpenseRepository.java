package com.cderc.backend.repository;

import com.cderc.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface  ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByChildIdAndOrganizationId(Long childId, Long organizationId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.child.id = :childId AND e.organization.id = :organizationId")
    BigDecimal sumByChildAndOrganization(Long childId, Long organizationId);

    Optional<Expense> findByIdAndChildIdAndOrganizationId(Long expenseId, Long childId, Long organizationId);
}
