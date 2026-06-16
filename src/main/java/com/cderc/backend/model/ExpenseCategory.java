package com.cderc.backend.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public enum ExpenseCategory {
    SCHOOL_FEE,
    SCHOOL_MATERIAL,
    FOOD,
    HEALTH,
    CLOTHES,
    TRANSPORT,
    OTHER
}
