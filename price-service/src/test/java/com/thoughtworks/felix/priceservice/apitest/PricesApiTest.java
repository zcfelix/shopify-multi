package com.thoughtworks.felix.priceservice.apitest;

import com.thoughtworks.felix.priceservice.support.ApiTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.thoughtworks.felix.priceservice.support.TestHelper.outToLog;
import static com.thoughtworks.felix.priceservice.support.TestHelper.readJsonFrom;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

public class PricesApiTest extends ApiTest{

    private static final String PRICES_URL = "/products/1/prices";
    private static final Logger LOGGER = LoggerFactory.getLogger(PricesApiTest.class);

    @Test
    public void should_return_201_when_create_store_success() {
        final String body = readJsonFrom("request/create-price-201.json");
        final Response response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(PRICES_URL)
                .then()
                .statusCode(201)
                .body("data.amount", is(200.89f))
                .body("links.self", endsWith("/products/1/prices"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }
}