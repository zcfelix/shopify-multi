package com.thoughtworks.felix.productservice.support;

import com.thoughtworks.felix.productservice.ProductServiceApplication;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceApplication.class,
        webEnvironment = DEFINED_PORT)
@ActiveProfiles(resolver = TestProfileResolver.class)
public abstract class ApiTest {

    @Autowired
    private TruncateDatabaseService service;

    @BeforeClass
    public static void beforeClass() {
        System.out.println(String.format("Api Tests resolved profiles are [%s].", System.getProperty("spring.profiles.active")));
        RestAssured.port = 8081;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @After
    public void tearDown() {
        service.truncate();
    }
}
