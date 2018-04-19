package com.thoughtworks.felix.priceservice.rest.application.service;

import com.thoughtworks.felix.priceservice.rest.dto.ErrorDTO;
import org.springframework.validation.BindingResult;

import java.util.List;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.LOWER_UNDERSCORE;
import static java.util.stream.Collectors.toList;

public class BindingResultResolver {
    public static List<ErrorDTO> parseErrors(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .map(e -> ErrorDTO
                        .builder()
                        .withField(LOWER_CAMEL.to(LOWER_UNDERSCORE, e.getField()))
                        .withRejectedValue(e.getRejectedValue() == null ? "" : e.getRejectedValue().toString())
                        .withMessage(e.getDefaultMessage()).build())
                .collect(toList());
    }
}
