package com.cderc.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data       // erzeugt Getter, Setter, toString, equals, hashCode
@NoArgsConstructor
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String healthStatus;

    private SchoolStatus schoolStatus;
    private String schoolClass;
    private String vocationalTrainingType;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
