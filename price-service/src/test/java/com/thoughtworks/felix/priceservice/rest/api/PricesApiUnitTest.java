package com.thoughtworks.felix.priceservice.rest.api;

import com.thoughtworks.felix.priceservice.domain.ProductService;
import com.thoughtworks.felix.priceservice.support.ApiUnitTest;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.thoughtworks.felix.priceservice.support.TestHelper.outToLog;
import static com.thoughtworks.felix.priceservice.support.TestHelper.readJsonFrom;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PricesApiUnitTest extends ApiUnitTest {

    private static final String PRICES_URL = "/products/1/prices";
    private static final String NON_EXIST_PRODUCT_PRICES_URL = "/products/0/prices";
    private static final Logger LOGGER = LoggerFactory.getLogger(PricesApiUnitTest.class);

    @Mock
    private ProductService service;

    @InjectMocks
    private PricesApi pricesApi;

    @Before
    public void setUp() {
        setUpApi(pricesApi);
//        when(service.isProductExist(anyLong())).thenReturn(false);
    }

    @Test
    public void should_400_when_create_price_with_invalid_amount() {
        final String body = readJsonFrom("request/create-price-invalid-amount-400.json");
        final MockMvcResponse response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(PRICES_URL)
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
                .post(PRICES_URL)
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
    @Ignore
    public void should_400_when_create_price_with_non_exist_product_id() {
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
}
