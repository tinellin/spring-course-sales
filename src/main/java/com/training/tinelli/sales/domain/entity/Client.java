package com.training.tinelli.sales.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client {

    public Client(Integer id, String name, String cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "cpf", columnDefinition = "CHAR(11)", nullable = false)
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Order> orders;
}
