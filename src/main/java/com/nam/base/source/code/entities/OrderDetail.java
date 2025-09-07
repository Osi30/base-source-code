package com.nam.base.source.code.entities;

import com.nam.base.source.code.utils.GenerateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @Column(name = "order_detail_id", length = 10)
    private String id;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @PrePersist
    public void generateId() {
        this.id = GenerateUtil.generateRandomWords(10);
    }
}
