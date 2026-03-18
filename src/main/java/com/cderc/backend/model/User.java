package com.cderc.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role; // ADMIN, SOCIAL_WORKER, VOLUNTEER

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
}
