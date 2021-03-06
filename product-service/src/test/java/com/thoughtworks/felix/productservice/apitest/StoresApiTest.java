package com.thoughtworks.felix.productservice.apitest;

import com.thoughtworks.felix.productservice.support.ApiTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.thoughtworks.felix.productservice.support.CustomMatchers.matchesRegex;
import static com.thoughtworks.felix.productservice.support.TestHelper.outToLog;
import static com.thoughtworks.felix.productservice.support.TestHelper.readJsonFrom;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/insert-store.sql"),
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:sql/insert-products.sql")
})
public class StoresApiTest extends ApiTest {

    private static final String STORES_URL = "/stores";
    private static final Logger LOGGER = LoggerFactory.getLogger(StoresApiTest.class);

    @Test
    public void should_return_201_when_create_store_success() {
        final String body = readJsonFrom("request/create-store-201.json");
        final Response response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(STORES_URL)
                .then()
                .statusCode(201)
                .body("data.name", is("pet store"))
                .body("links.self", matchesRegex(".*/stores/[0-9]+"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_200_when_list_all_stores_success() {
        final Response response = given()
                .when()
                .get(STORES_URL)
                .then()
                .statusCode(200)
                .body("data[0].name", is("pet-store"))
                .body("links.self", endsWith("/stores"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_200_when_show_a_store_success() {
        final Response response = given()
                .when()
                .get(STORES_URL + "/1")
                .then()
                .statusCode(200)
                .body("data.name", is("pet-store"))
                .body("links.self", matchesRegex(".*/stores/[0-9]+"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_201_when_create_a_product_success() {
        final String body = readJsonFrom("request/create-product-201.json");
        final Response response = given()
                .when()
                .contentType(JSON)
                .body(body)
                .post(STORES_URL + "/1/products")
                .then()
                .statusCode(201)
                .body("links.self", matchesRegex(".*/stores/[0-9]+/products/[0-9]+"))
                .body("data.name", is("pet test"))
                .body("data.description", is("lovely pet test"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }


    @Test
    public void should_return_200_when_list_products_of_store_success() {
        final Response response = given()
                .when()
                .get(STORES_URL + "/1/products")
                .then()
                .statusCode(200)
                .body("links.self", matchesRegex(".*/stores/[0-9]+/products"))
                .body("data.name", hasItems("dog", "cat"))
                .body("data.size()", is(3))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    public void should_return_204_when_unloading_product_success() {
        given()
                .when()
                .put(STORES_URL + "/1/products/1/unloading")
                .then()
                .statusCode(204);
    }

    @Test
    public void should_return_204_when_uploading_product_success() {
        given()
                .when()
                .put(STORES_URL + "/1/products/4/uploading")
                .then()
                .statusCode(204);
    }
}
