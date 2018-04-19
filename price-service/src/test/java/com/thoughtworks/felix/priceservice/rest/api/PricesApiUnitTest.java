package com.thoughtworks.felix.priceservice.rest.api;

import com.thoughtworks.felix.priceservice.domain.PriceRepository;
import com.thoughtworks.felix.priceservice.domain.ProductService;
import com.thoughtworks.felix.priceservice.support.ApiUnitTest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.thoughtworks.felix.priceservice.support.TestHelper.outToLog;
import static com.thoughtworks.felix.priceservice.support.TestHelper.readJsonFrom;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.ArgumentMatchers.anyLong;

public class PricesApiUnitTest extends ApiUnitTest {

    private static final String PRODUCTS_1_PRICES_URL = "/products/1/prices";
    private static final String PRODUCTS_1_CURRENT_PRICE = "products/1/current-price";
    private static final String NON_EXIST_PRODUCT_PRICES_URL = "/products/0/prices";
    private static final Logger LOGGER = LoggerFactory.getLogger(PricesApiUnitTest.class);

    @Mock
    private ProductService productService;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PricesApi pricesApi;

    @Before
    public void setUp() {
        setUpApi(pricesApi);
        Mockito.when(productService.isProductExist(1L)).thenReturn(true);
    }

    @Test
    public void should_400_when_create_price_with_invalid_amount() {
        final String body = readJsonFrom("request/create-price-invalid-amount-400.json");
        final MockMvcResponse response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(PRODUCTS_1_PRICES_URL)
                .then()
                .contentType(JSON)
                .statusCode(400)
                .body("field", hasItems("amount"))
                .body("message", hasItems("must be greater than or equal to 0"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_400_when_create_price_with_non_number_amount() {
        final String body = readJsonFrom("request/create-price-non-number-amount-400.json");
        final MockMvcResponse response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(PRODUCTS_1_PRICES_URL)
                .then()
                .contentType(JSON)
                .statusCode(400)
                .body("field", hasItems("amount"))
                .body("message", hasItems("field format error"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_400_when_create_price_with_non_exist_product_id() {
        Mockito.when(productService.isProductExist(0L)).thenReturn(false);

        final String body = readJsonFrom("request/create-price-201.json");
        final MockMvcResponse response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(NON_EXIST_PRODUCT_PRICES_URL)
                .then()
                .contentType(JSON)
                .statusCode(404)
                .body("message", hasItems("product not found"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_404_when_list_prices_with_non_exist_product_id() {
        Mockito.when(productService.isProductExist(0L)).thenReturn(false);

        final MockMvcResponse response = given()
                .when()
                .get(NON_EXIST_PRODUCT_PRICES_URL)
                .then()
                .contentType(JSON)
                .statusCode(404)
                .body("message", hasItems("product not found"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_404_when_show_price_with_exist_product_id_but_non_exist_price_id() {
        Mockito.when(priceRepository.findById(0L)).thenReturn(Optional.empty());
        final MockMvcResponse response = given()
                .when()
                .get(PRODUCTS_1_PRICES_URL + "/0")
                .then()
                .contentType(JSON)
                .statusCode(404)
                .body("message", hasItems("price not found"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_404_when_show_current_price_with_exist_product_id_but_non_prices() {
        Mockito.when(priceRepository.findTopByProductIdOrderByCreatedAtDesc(anyLong())).thenReturn(Optional.empty());
        final MockMvcResponse response = given()
                .when()
                .get(PRODUCTS_1_CURRENT_PRICE)
                .then()
                .contentType(JSON)
                .statusCode(404)
                .body("message", hasItems("no price yet"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }
}
