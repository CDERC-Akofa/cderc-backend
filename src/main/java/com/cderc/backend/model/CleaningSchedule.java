package com.cderc.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Table(name = "cleaning_schedules")
@Data
public class CleaningSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate weekStart;
    private LocalDate weekEnd;

    @ManyToOne
    private Member personOne;

    @ManyToOne
    private Member personTwo;

    @ManyToOne
    private Organization organization;

}
