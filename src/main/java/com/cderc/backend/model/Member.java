package com.cderc.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data       // erzeugt Getter, Setter, toString, equals, hashCode
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private String address;
    private String city;
    private String country;

    private LocalDate birthDate;
    private LocalDate joinedAt;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @ManyToOne
    private Organization organization;

}
