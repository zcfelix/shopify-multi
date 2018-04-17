package com.thoughtworks.felix.productservice.rest.api;

import com.thoughtworks.felix.productservice.domain.Product;
import com.thoughtworks.felix.productservice.domain.ProductRepository;
import com.thoughtworks.felix.productservice.rest.dto.BatchResource;
import com.thoughtworks.felix.productservice.rest.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public BatchResource listAllProducts() {
        final List<Product> all = productRepository.findAll();
        final List<ProductDTO> productDTOS = all
                .stream()
                .map(x -> modelMapper.map(x, ProductDTO.class))
                .collect(toList());
        URI link = linkTo(methodOn(ProductsApi.class).listAllProducts()).toUri();

        return new BatchResource<>(productDTOS, link);
    }
}
