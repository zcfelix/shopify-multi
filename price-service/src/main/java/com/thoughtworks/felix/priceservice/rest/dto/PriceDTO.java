package com.thoughtworks.felix.priceservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PriceDTO {

    @JsonProperty
    private Long id;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty
    @Min(0)
    private BigDecimal amount;

    @JsonProperty("crated_at")
    private Timestamp createdAt;

    private void setId(Long id) {
        this.id = id;
    }

    private void setProductId(Long productId) {
        this.productId = productId;
    }

    private void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }
}
