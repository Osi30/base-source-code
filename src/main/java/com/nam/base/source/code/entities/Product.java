package com.nam.base.source.code.entities;

import com.nam.base.source.code.enums.ProductStatus;
import com.nam.base.source.code.utils.GenerateUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_id", length = 10)
    private String id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private ProductStatus status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(name = "stock")
    @Min(value = 0, message = "Stock is greater than or equal 0")
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @PrePersist
    public void generateId() {
        this.id = GenerateUtil.generateRandomWords(10);
    }
}
