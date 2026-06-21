package com.cderc.backend.repository;

import com.cderc.backend.model.EventExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EventExpenseRepository extends JpaRepository<EventExpense, Long> {

    List<EventExpense> findByEventIdAndOrganizationId(Long eventId, Long organizationId);

    Optional<EventExpense> findByIdAndEventIdAndOrganizationId(
            Long expenseId,
            Long eventId,
            Long organizationId
    );

    @Query("""
           SELECT COALESCE(SUM(e.amount), 0)
           FROM EventExpense e
           WHERE e.event.id = :eventId
           AND e.organization.id = :organizationId
           """)
    BigDecimal sumByEventAndOrganization(Long eventId, Long organizationId);
}
