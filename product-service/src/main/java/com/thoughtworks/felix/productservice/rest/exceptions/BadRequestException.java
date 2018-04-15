package com.thoughtworks.felix.productservice.rest.exceptions;

import com.thoughtworks.felix.productservice.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends ShopifyInternalException {

    public BadRequestException(List<? extends ErrorDTO> errors) {
        super(errors);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
