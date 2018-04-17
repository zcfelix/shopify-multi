package com.thoughtworks.felix.productservice.apitest;

import com.thoughtworks.felix.productservice.support.ApiTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.thoughtworks.felix.productservice.support.TestHelper.outToLog;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/insert-store.sql"),
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/insert-products.sql")
})
public class ProductsApiTest extends ApiTest{

    private static final String PRODUCTS_URL = "/products";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsApiTest.class);

    @Test
    public void should_return_200_when_list_all_products() {
        final Response response = given()
                .when()
                .get(PRODUCTS_URL)
                .then()
                .statusCode(200)
                .body("data.size()", is(4))
                .body("data.name", hasItems("dog", "cat", "elephant", "fish"))
                .body("links.self", endsWith("/products"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_200_when_find_product_success() {
        final Response response = given()
                .when()
                .get(PRODUCTS_URL + "/1")
                .then()
                .statusCode(200)
                .body("data.name", is("dog"))
                .body("links.self", endsWith("/products/1"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }
}