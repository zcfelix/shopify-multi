package com.thoughtworks.felix.priceservice.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", decode404 = true)
public interface ProductClient {

    @GetMapping(value = "/products/{id}")
    ResponseEntity showProduct(@PathVariable("id") Long id);
}
