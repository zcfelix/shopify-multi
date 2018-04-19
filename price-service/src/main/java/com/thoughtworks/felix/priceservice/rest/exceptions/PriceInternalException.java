package com.thoughtworks.felix.priceservice.rest.exceptions;

import com.thoughtworks.felix.priceservice.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class PriceInternalException extends RuntimeException {
    private List<? extends ErrorDTO> errors;

    public PriceInternalException(List<? extends ErrorDTO> errors) {
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
