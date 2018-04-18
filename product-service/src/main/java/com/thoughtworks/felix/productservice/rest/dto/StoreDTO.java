package com.thoughtworks.felix.productservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StoreDTO {
    @JsonProperty
    private Long id;

    @JsonProperty("owner_id")
    @NotNull
    @Min(value = 0L, message = "The value must be positive")
    private Long ownerId;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    private String description;

    @SuppressWarnings("unused")
    private StoreDTO() {
    }

    @SuppressWarnings("unused")
    private StoreDTO setId(Long id) {
        this.id = id;
        return this;
    }

    @SuppressWarnings("unused")
    private StoreDTO setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @SuppressWarnings("unused")
    private StoreDTO setName(String name) {
        this.name = name;
        return this;
    }

    @SuppressWarnings("unused")
    private StoreDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    @SuppressWarnings("unused")
    private Long getId() {
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
