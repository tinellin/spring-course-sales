package com.training.tinelli.sales.domain.entity;

import com.training.tinelli.sales.domain.enums.OrderStatus;
import com.training.tinelli.sales.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Entity
@Table(name = "Purchase_Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Column(name = "order_data")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<OrderItem> items;
}


