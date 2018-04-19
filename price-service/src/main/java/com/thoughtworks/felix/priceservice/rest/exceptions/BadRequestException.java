package com.thoughtworks.felix.priceservice.rest.exceptions;

import com.thoughtworks.felix.priceservice.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends PriceInternalException {

    public BadRequestException(List<? extends ErrorDTO> errors) {
        super(errors);
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
