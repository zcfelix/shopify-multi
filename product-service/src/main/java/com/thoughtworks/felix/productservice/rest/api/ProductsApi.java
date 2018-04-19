package com.thoughtworks.felix.productservice.rest.api;

import com.thoughtworks.felix.productservice.domain.Product;
import com.thoughtworks.felix.productservice.domain.ProductRepository;
import com.thoughtworks.felix.productservice.rest.dto.BatchResource;
import com.thoughtworks.felix.productservice.rest.dto.ErrorDTO;
import com.thoughtworks.felix.productservice.rest.dto.ProductDTO;
import com.thoughtworks.felix.productservice.rest.dto.SingleResource;
import com.thoughtworks.felix.productservice.rest.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class ProductsApi {

    private final ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductsApi(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(PRIVATE).setMethodAccessLevel(PRIVATE);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity listAllProducts() {
        final List<Product> all = productRepository.findAll();
        final List<ProductDTO> productDTOS = all
                .stream()
                .map(x -> modelMapper.map(x, ProductDTO.class))
                .collect(toList());
        URI link = linkTo(methodOn(ProductsApi.class).listAllProducts()).toUri();

        return ResponseEntity.ok(new BatchResource<>(productDTOS, link));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity showProduct(@PathVariable("id") Long id) {

        URI link = linkTo(methodOn(ProductsApi.class).showProduct(id)).toUri();

        return productRepository.findById(id)
                .map(x -> ResponseEntity.ok(new SingleResource<>(modelMapper.map(x, ProductDTO.class), link)))
                .orElseThrow(() -> new NotFoundException(ErrorDTO.builder().withMessage("product not found").build()));

    }
}