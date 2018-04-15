package com.thoughtworks.felix.productservice.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    private String name;

    private String description;

    private Store() {
    }

    public Store(String name, Long ownerId) {
        this.ownerId = ownerId;
        this.name = name;
    }

    public Store(String name, Long ownerId, String description) {
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
