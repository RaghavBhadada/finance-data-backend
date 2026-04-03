package com.raghav.desibusiness.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    private boolean emailVerified;

    @JsonIgnore
    private boolean phoneVerified;

    @Enumerated(EnumType.STRING)
    private Status status;
}
