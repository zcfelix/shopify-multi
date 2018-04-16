package com.thoughtworks.felix.productservice.domain;

import javax.persistence.*;

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

    @SuppressWarnings("unused")
    private Product() {
    }

    public Product(Long storeId, String name, String description) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
