package com.cderc.backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "event_expenses")
public class EventExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private BigDecimal amount;

    private LocalDate expenseDate;

    @Enumerated(EnumType.STRING)
    private EventExpenseCategory category;

    private String description;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Organization organization;
}
