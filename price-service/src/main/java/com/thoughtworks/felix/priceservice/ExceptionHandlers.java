package com.thoughtworks.felix.priceservice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.thoughtworks.felix.priceservice.rest.dto.ErrorDTO;
import com.thoughtworks.felix.priceservice.rest.exceptions.PriceInternalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = PriceInternalException.class)
    public ResponseEntity shopifyInternalExceptionHandler(PriceInternalException e) {
        return ResponseEntity.status(e.httpStatus()).body(e.getErrors());
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
