package com.thoughtworks.felix.priceservice.apitest;

import com.thoughtworks.felix.priceservice.domain.ProductService;
import com.thoughtworks.felix.priceservice.support.ApiTest;
import com.thoughtworks.felix.priceservice.support.CustomMatchers;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.thoughtworks.felix.priceservice.support.CustomMatchers.matchesRegex;
import static com.thoughtworks.felix.priceservice.support.TestHelper.outToLog;
import static com.thoughtworks.felix.priceservice.support.TestHelper.readJsonFrom;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/insert-prices.sql")
})
public class PricesApiTest extends ApiTest {

    private static final String PRICES_URL = "/products/1/prices";
    private static final Logger LOGGER = LoggerFactory.getLogger(PricesApiTest.class);
    private static final String CURRENT_PRICE_URL = "/products/1/current-price";

    @MockBean
    private ProductService productService;

    @Before
    public void setUp() {
        when(productService.isProductExist(eq(1L))).thenReturn(true);
    }

    @Test
    public void should_return_201_when_create_product_price_success() {
        final String body = readJsonFrom("request/create-price-201.json");
        final Response response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(PRICES_URL)
                .then()
                .statusCode(201)
                .body("data.amount", is(200.89f))
                .body("links.self", matchesRegex(".*/products/1/prices/[0-9]+"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_200_when_list_product_prices_success() {

        final Response response = given()
                .when()
                .get(PRICES_URL)
                .then()
                .statusCode(200)
                .body("links.self", endsWith("/products/1/prices"))
                .body("data.amount", hasItems(3.88f, 4.99f))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_200_when_find_a_product_price_success() {

        final Response response = given()
                .when()
                .get(PRICES_URL + "/1")
                .then()
                .statusCode(200)
                .body("links.self", matchesRegex(".*/products/1/prices/[0-9]+"))
                .body("data.amount", is(3.88f))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_200_when_find_a_product_current_price_success() {

        final Response response = given()
                .when()
                .get(CURRENT_PRICE_URL)
                .then()
                .statusCode(200)
                .body("links.self", endsWith("/products/1/current-price"))
                .body("data.amount", is(3.88f))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

}