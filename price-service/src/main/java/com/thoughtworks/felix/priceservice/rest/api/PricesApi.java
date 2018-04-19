package com.thoughtworks.felix.priceservice.rest.api;

import com.thoughtworks.felix.priceservice.domain.Price;
import com.thoughtworks.felix.priceservice.domain.PriceRepository;
import com.thoughtworks.felix.priceservice.domain.ProductService;
import com.thoughtworks.felix.priceservice.rest.dto.PriceDTO;
import com.thoughtworks.felix.priceservice.rest.dto.SingleResource;
import com.thoughtworks.felix.priceservice.rest.exceptions.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.thoughtworks.felix.priceservice.rest.application.service.BindingResultResolver.parseErrors;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class PricesApi {

    private final PriceRepository repository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public PricesApi(PriceRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(PRIVATE).setMethodAccessLevel(PRIVATE);
    }

    @PostMapping(value = "/{id}/prices", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResource createPrice(@PathVariable("id") Long productId,
                                      @Valid @RequestBody PriceDTO priceDTO,
                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(parseErrors(result));
        }

//        final SingleResource resource = productService.showProduct(productId);

//        if (productService.showProduct(productId)) {
//            throw new NotFoundException(ErrorDTO.builder().withMessage("product not found").build());
//        }

        final Price price = new Price(productId, priceDTO.getAmount());
        final Price saved = repository.save(price);

        final PriceDTO responseDTO = modelMapper.map(saved, PriceDTO.class);
        URI link = linkTo(methodOn(PricesApi.class).createPrice(productId, priceDTO, result)).toUri();

        return new SingleResource<>(responseDTO, link);
    }
}
