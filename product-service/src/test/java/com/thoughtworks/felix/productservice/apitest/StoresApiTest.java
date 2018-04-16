package com.thoughtworks.felix.productservice.apitest;

import com.thoughtworks.felix.productservice.support.ApiTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static com.thoughtworks.felix.productservice.support.TestHelper.outToLog;
import static com.thoughtworks.felix.productservice.support.TestHelper.readJsonFrom;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

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
                .body("links.self", endsWith("/stores"))
                .extract()
                .response();
        outToLog(LOGGER, response);
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "classpath:/sql/insert-store.sql")
    })
    public void should_return_200_when_list_all_stores_success() {
        final Response response = given()
                .when()
                .get(STORES_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();
        outToLog(LOGGER, response);
    }
}
