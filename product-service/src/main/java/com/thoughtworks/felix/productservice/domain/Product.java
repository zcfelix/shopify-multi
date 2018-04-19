package com.thoughtworks.felix.productservice.domain;

import javax.persistence.*;

import static com.thoughtworks.felix.productservice.domain.ProductState.SHELVE;
import static com.thoughtworks.felix.productservice.domain.ProductState.UNSHELVE;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @SuppressWarnings("unused")
    private ProductState state;

    @SuppressWarnings("unused")
    private Product() {
    }

    public Product(Long storeId, String name, String description) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.state = SHELVE;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private Long getStoreId() {
        return storeId;
    }

    @SuppressWarnings("unused")
    private ProductState getState() {
        return state;
    }

    public Product unload() {
        this.state = UNSHELVE;
        return this;
    }

    public Product upload() {
        this.state = SHELVE;
        return this;
    }
}
