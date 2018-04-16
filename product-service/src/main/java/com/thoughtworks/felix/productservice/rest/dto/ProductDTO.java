package com.thoughtworks.felix.productservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDTO {

    @JsonProperty
    private Long id;

    @JsonProperty("store_id")
    private Long storeId;

    @JsonProperty
    @NotBlank
    private String name;

    @JsonProperty
    private String description;

    @JsonCreator
    private ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    private void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @SuppressWarnings("unused")
    private void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    private void setDescription(String description) {
        this.description = description;
    }
}
