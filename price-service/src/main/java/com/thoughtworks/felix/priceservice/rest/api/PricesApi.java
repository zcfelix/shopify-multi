package com.thoughtworks.felix.priceservice.rest.api;

import com.thoughtworks.felix.priceservice.domain.Price;
import com.thoughtworks.felix.priceservice.domain.PriceRepository;
import com.thoughtworks.felix.priceservice.domain.ProductService;
import com.thoughtworks.felix.priceservice.rest.dto.BatchResource;
import com.thoughtworks.felix.priceservice.rest.dto.ErrorDTO;
import com.thoughtworks.felix.priceservice.rest.dto.PriceDTO;
import com.thoughtworks.felix.priceservice.rest.dto.SingleResource;
import com.thoughtworks.felix.priceservice.rest.exceptions.BadRequestException;
import com.thoughtworks.felix.priceservice.rest.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.thoughtworks.felix.priceservice.rest.application.service.BindingResultResolver.parseErrors;
import static java.util.stream.Collectors.toList;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class PricesApi {

    private final PriceRepository repository;
    private final ModelMapper modelMapper;
    private ProductService productService;

    @Autowired
    public PricesApi(PriceRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(PRIVATE).setMethodAccessLevel(PRIVATE);
    }

    @PostMapping(value = "/{id}/prices", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createPrice(@PathVariable("id") Long productId,
                                      @Valid @RequestBody PriceDTO priceDTO,
                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(parseErrors(result));
        }

        if (!productService.isProductExist(productId)) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
        }

        final Price price = new Price(productId, priceDTO.getAmount());
        final Price saved = repository.save(price);

        final PriceDTO responseDTO = modelMapper.map(saved, PriceDTO.class);
        URI link = linkTo(methodOn(PricesApi.class)
                .createPrice(productId, priceDTO, result))
                .slash(saved.getId()).toUri();

        return ResponseEntity.created(link).body(new SingleResource<>(responseDTO, link));

    }

    @GetMapping(value = "/{id}/prices", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity listPrices(@PathVariable("id") Long productId) {

        if (!productService.isProductExist(productId)) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
        }

        final List<Price> prices = repository.findAllByProductId(productId);

        final List<PriceDTO> priceDTOS = prices.stream().map(x -> modelMapper.map(x, PriceDTO.class)).collect(toList());
        final URI link = linkTo(methodOn(PricesApi.class).listPrices(productId)).toUri();

        return ResponseEntity.ok(new BatchResource<>(priceDTOS, link));
    }

    @GetMapping(value = "/{id}/prices/{priceId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity showPrice(@PathVariable("id") Long productId,
                                    @PathVariable("priceId") Long priceId) {
        if (!productService.isProductExist(productId)) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
        }

        URI link = linkTo(methodOn(PricesApi.class).showPrice(productId, priceId)).toUri();

        return repository.findById(priceId)
                .map(x -> ResponseEntity.ok(new SingleResource<>(modelMapper.map(x, PriceDTO.class), link)))
                .orElseThrow(() -> new NotFoundException(ErrorDTO.builder().withMessage("price not found").build()));
    }

    @GetMapping(value = "{id}/current-price", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity showCurrentPrice(@PathVariable("id") Long productId) {
        if (!productService.isProductExist(productId)) {
            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
        }

        URI link = linkTo(methodOn(PricesApi.class).showCurrentPrice(productId)).toUri();

        return repository.findTopByProductIdOrderByCreatedAtDesc(productId)
                .map(x -> ResponseEntity.ok(new SingleResource<>(modelMapper.map(x, PriceDTO.class), link)))
                .orElseThrow(() -> new NotFoundException(ErrorDTO.builder().withMessage("no price yet").build()));
    }
}
