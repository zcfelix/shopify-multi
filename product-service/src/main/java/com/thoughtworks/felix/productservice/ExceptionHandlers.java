package com.thoughtworks.felix.productservice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thoughtworks.felix.productservice.rest.dto.ErrorDTO;
import com.thoughtworks.felix.productservice.rest.exceptions.ShopifyInternalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = ShopifyInternalException.class)
    public ResponseEntity shopifyInternalExceptionHandler(ShopifyInternalException e) {
        return ResponseEntity.badRequest().body(e.getErrors());
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    public ResponseEntity invalidFormatExceptionHandler(InvalidFormatException e) {
        return ResponseEntity.badRequest().body(toErrors(e));
    }

    private List<ErrorDTO> toErrors(InvalidFormatException e) {
        return e.getPath()
                .stream()
                .map(x -> ErrorDTO.builder()
                        .withField(x.getFieldName())
                        .withMessage("field format error")
                        .withRejectedValue(e.getValue().toString())
                        .build())
                .collect(toList());
    }
}
