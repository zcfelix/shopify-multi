package com.thoughtworks.felix.priceservice.rest.exceptions;

import com.thoughtworks.felix.priceservice.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

import static java.util.Arrays.asList;

public class NotFoundException extends PriceInternalException {

    public NotFoundException(ErrorDTO... errors) {
        super(asList(errors));
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
