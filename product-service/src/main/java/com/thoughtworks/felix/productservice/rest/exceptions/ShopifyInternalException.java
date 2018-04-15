package com.thoughtworks.felix.productservice.rest.exceptions;

import com.thoughtworks.felix.productservice.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class ShopifyInternalException extends RuntimeException {
    private List<? extends ErrorDTO> errors;

    public ShopifyInternalException(List<? extends ErrorDTO> errors) {
        super();
        this.errors = errors;
    }

    public abstract HttpStatus httpStatus();

    public boolean is4xxFailure() {
        return httpStatus().is4xxClientError();
    }

    public boolean is5xxFailure() {
        return httpStatus().is5xxServerError();
    }

    public List<? extends ErrorDTO> getErrors() {
        return errors;
    }
}
