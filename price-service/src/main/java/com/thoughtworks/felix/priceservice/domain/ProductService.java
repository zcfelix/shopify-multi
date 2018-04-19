package com.thoughtworks.felix.priceservice.domain;

import com.thoughtworks.felix.priceservice.rest.dto.SingleResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", decode404 = true)
public interface ProductService {

    @GetMapping(value = "/products/{id}")
    SingleResource showProduct(@PathVariable("id") Long id);
}
