package com.cderc.backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String healthStatus;
    private String schoolStatus;

    @ManyToOne
    private Organization organization;

}
