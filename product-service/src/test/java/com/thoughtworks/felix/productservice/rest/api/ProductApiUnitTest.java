package com.thoughtworks.felix.productservice.rest.api;

import com.thoughtworks.felix.productservice.domain.ProductRepository;
import com.thoughtworks.felix.productservice.support.ApiUnitTest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.thoughtworks.felix.productservice.support.TestHelper.outToLog;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ProductApiUnitTest extends ApiUnitTest {

    private static final String PRODUCTS_URL = "/products";
    private static final Logger LOGGER = LoggerFactory.getLogger(StoresApiUnitTest.class);

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductsApi productsApi;

    @Before
    public void setUp() {
        setUpApi(productsApi);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
    }

    @Test
    public void should_404_when_product_not_found() {
        final MockMvcResponse response = given()
                .when()
                .get(PRODUCTS_URL + "/1")
                .then()
                .statusCode(404)
                .body("message", hasItems("product not found"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }
}
