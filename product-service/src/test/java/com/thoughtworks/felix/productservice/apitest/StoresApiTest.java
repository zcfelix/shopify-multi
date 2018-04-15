package com.thoughtworks.felix.productservice.apitest;

import com.thoughtworks.felix.productservice.support.ApiTest;
import com.thoughtworks.felix.productservice.support.TestHelper;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.thoughtworks.felix.productservice.support.TestHelper.outToLog;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

public class StoresApiTest extends ApiTest {

    private static final String CREATE_STORE_URL = "/stores";
    private static final Logger LOGGER = LoggerFactory.getLogger(StoresApiTest.class);

    @Test
    public void should_return_201_when_create_store_success() {
        final String body = TestHelper.readJsonFrom("request/create-store-201.json");
        final Response response = given()
                .contentType(JSON)
                .body(body)
                .when()
                .post(CREATE_STORE_URL)
                .then()
                .statusCode(201)
                .body("name", is("pet store"))
                .body("_links.self.href", endsWith("/stores"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }
}
