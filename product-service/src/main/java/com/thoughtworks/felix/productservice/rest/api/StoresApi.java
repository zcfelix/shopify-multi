package com.thoughtworks.felix.productservice.rest.api;

import com.thoughtworks.felix.productservice.application.service.BindingResultResolver;
import com.thoughtworks.felix.productservice.domain.Store;
import com.thoughtworks.felix.productservice.domain.StoreRepository;
import com.thoughtworks.felix.productservice.rest.dto.StoreDTO;
import com.thoughtworks.felix.productservice.rest.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/stores")
public class StoresApi {

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping(produces = "application/json")
    @ResponseStatus(CREATED)
    public Resource createStore(@Valid @RequestBody StoreDTO storeDTO,
                                BindingResult result) {

        if (result.hasErrors()) {
            throw new BadRequestException(BindingResultResolver.parseErrors(result));
        }

        final Store store = storeDTO.toDomain();

        final Store saved = storeRepository.save(store);

        final StoreDTO responseDTO = new StoreDTO(saved);

        Link link = linkTo(methodOn(StoresApi.class).createStore(storeDTO, result)).withSelfRel();

        return new Resource<>(responseDTO, link);
    }
}
