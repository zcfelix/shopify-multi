package com.thoughtworks.felix.productservice.rest.api;

import com.thoughtworks.felix.productservice.domain.Product;
import com.thoughtworks.felix.productservice.domain.ProductRepository;
import com.thoughtworks.felix.productservice.domain.Store;
import com.thoughtworks.felix.productservice.domain.StoreRepository;
import com.thoughtworks.felix.productservice.rest.dto.*;
import com.thoughtworks.felix.productservice.rest.exceptions.BadRequestException;
import com.thoughtworks.felix.productservice.rest.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.thoughtworks.felix.productservice.application.service.BindingResultResolver.parseErrors;
import static java.util.stream.Collectors.toList;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/stores")
public class StoresApi {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Autowired
    public StoresApi(StoreRepository storeRepository, ProductRepository productRepository) {
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(PRIVATE).setMethodAccessLevel(PRIVATE);
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(CREATED)
    public SingleResource createStore(@Valid @RequestBody StoreDTO storeDTO,
                                      BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException(parseErrors(result));
        }

        final Store saved = storeRepository.save(modelMapper.map(storeDTO, Store.class));
        URI link = linkTo(methodOn(StoresApi.class).createStore(storeDTO, result)).toUri();

        return new SingleResource<>(modelMapper.map(saved, StoreDTO.class), link);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(OK)
    public BatchResource listStores() {

        final List<Store> all = storeRepository.findAll();
        final List<StoreDTO> storeDTOS = all.stream().map(x -> modelMapper.map(x, StoreDTO.class)).collect(toList());
        final URI link = linkTo(methodOn(StoresApi.class).listStores()).toUri();

        return new BatchResource<>(storeDTOS, link);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(OK)
    public SingleResource showStore(@PathVariable("id") Long id) {
        final Optional<Store> storeOptional = storeRepository.findById(id);
        if (!storeOptional.isPresent()) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("store not found").build());
        }

        final StoreDTO storeDTO = modelMapper.map(storeOptional.get(), StoreDTO.class);
        final URI link = linkTo(methodOn(StoresApi.class).showStore(id)).toUri();

        return new SingleResource<>(storeDTO, link);
    }

    @PostMapping(value = "{id}/products", produces = "application/json")
    @ResponseStatus(CREATED)
    public SingleResource createProduct(@PathVariable("id") Long storeId,
                                        @Valid @RequestBody ProductDTO productDTO,
                                        BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(parseErrors(result));
        }

        final Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (!storeOptional.isPresent()) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("store not found").build());
        }

        final Product product = new Product(storeId, productDTO.getName(), productDTO.getDescription());
        final Product saved = productRepository.save(product);
        final ProductDTO responseDTO = modelMapper.map(saved, ProductDTO.class);
        final URI link = linkTo(methodOn(StoresApi.class).createProduct(storeId, productDTO, result)).toUri();

        return new SingleResource<>(responseDTO, link);
    }

    @GetMapping(value = "{id}/products", produces = "application/json")
    @ResponseStatus(OK)
    public BatchResource listProducts(@PathVariable("id") Long storeId) {

        final List<Product> allInStore = productRepository.findAllByStoreId(storeId);
        final URI link = linkTo(methodOn(StoresApi.class).listProducts(storeId)).toUri();

        return new BatchResource<>(allInStore.stream().map(x -> modelMapper.map(x, ProductDTO.class)).collect(toList()), link);
    }

    @PutMapping(value = "{id}/products/{productId}/unloading", produces = "application/json")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity unloadProduct(@PathVariable("id") Long storeId,
                                        @PathVariable("productId") Long productId) {
        final Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (!storeOptional.isPresent()) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("store not found").build());
        }

        final Optional<Product> productOptional = productRepository.findByIdAndStoreId(productId, storeId);
        if (!productOptional.isPresent()) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
        }

        final Product unloadingProduct = productOptional.get().unload();
        productRepository.save(unloadingProduct);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "{id}/products/{productId}/uploading", produces = "application/json")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity uploadProduct(@PathVariable("id") Long storeId,
                                        @PathVariable("productId") Long productId) {
        final Optional<Store> storeOptional = storeRepository.findById(storeId);
        if (!storeOptional.isPresent()) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("store not found").build());
        }

        final Optional<Product> productOptional = productRepository.findByIdAndStoreId(productId, storeId);
        if (!productOptional.isPresent()) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
        }

        final Product uploadingProduct = productOptional.get().upload();
        productRepository.save(uploadingProduct);

        return ResponseEntity.noContent().build();
    }

}
