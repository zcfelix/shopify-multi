package com.thoughtworks.felix.productservice.rest.api;

import com.thoughtworks.felix.productservice.application.service.BindingResultResolver;
import com.thoughtworks.felix.productservice.domain.Store;
import com.thoughtworks.felix.productservice.domain.StoreRepository;
import com.thoughtworks.felix.productservice.rest.dto.BatchResource;
import com.thoughtworks.felix.productservice.rest.dto.ErrorDTO;
import com.thoughtworks.felix.productservice.rest.dto.SingleResource;
import com.thoughtworks.felix.productservice.rest.dto.StoreDTO;
import com.thoughtworks.felix.productservice.rest.exceptions.BadRequestException;
import com.thoughtworks.felix.productservice.rest.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/stores")
public class StoresApi {

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping(produces = "application/json")
    @ResponseStatus(CREATED)
    public SingleResource createStore(@Valid @RequestBody StoreDTO storeDTO,
                                      BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException(BindingResultResolver.parseErrors(result));
        }

        final Store saved = storeRepository.save(storeDTO.toDomain());
        final StoreDTO responseDTO = new StoreDTO(saved);
        URI link = linkTo(methodOn(StoresApi.class).createStore(storeDTO, result)).toUri();

        return new SingleResource<>(responseDTO, link);
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(OK)
    public BatchResource listStores() {

        final List<Store> all = storeRepository.findAll();
        final List<StoreDTO> storeDTOS = all.stream().map(StoreDTO::new).collect(toList());
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

        final StoreDTO storeDTO = new StoreDTO(storeOptional.get());
        final URI link = linkTo(methodOn(StoresApi.class).showStore(id)).toUri();

        return new SingleResource<>(storeDTO, link);
    }
}
