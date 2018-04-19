package com.thoughtworks.felix.priceservice.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    private BigDecimal amount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    private Price() {
    }

    public Price(Long productId, BigDecimal amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    private void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    private void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
