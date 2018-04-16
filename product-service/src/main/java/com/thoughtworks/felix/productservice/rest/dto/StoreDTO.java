package com.thoughtworks.felix.productservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.thoughtworks.felix.productservice.domain.Store;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName("data")
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

    @JsonCreator
    private StoreDTO() {
    }

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.ownerId = store.getOwnerId();
        this.name = store.getName();
        this.description = store.getDescription();
    }

    public Store toDomain() {
        return new Store(name, ownerId, description);
    }

    public StoreDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public StoreDTO setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public StoreDTO setName(String name) {
        this.name = name;
        return this;
    }

    public StoreDTO setDescription(String description) {
        this.description = description;
        return this;
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
