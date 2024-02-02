package com.training.tinelli.sales.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @NotNull(message = "{campo.username.obrigatorio}")
    private String username;

    @Column
    @NotNull(message = "{campo.password.obrigatorio}")
    private String password;

    @Column
    private boolean admin;
}
