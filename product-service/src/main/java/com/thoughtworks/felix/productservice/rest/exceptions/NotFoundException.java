package com.thoughtworks.felix.productservice.rest.exceptions;

import com.thoughtworks.felix.productservice.rest.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

import static java.util.Arrays.asList;

public class NotFoundException extends ShopifyInternalException {

    public NotFoundException(ErrorDTO... errors) {
        super(asList(errors));
    }

    @Override
    public HttpStatus httpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
