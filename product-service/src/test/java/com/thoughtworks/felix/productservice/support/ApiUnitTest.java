package com.thoughtworks.felix.productservice.support;

import com.thoughtworks.felix.productservice.ExceptionHandlers;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public abstract class ApiUnitTest {
    public void setUpApi(Object api) {
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(api).setControllerAdvice(new ExceptionHandlers())
        );
    }
}
