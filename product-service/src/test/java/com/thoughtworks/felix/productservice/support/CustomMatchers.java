package com.thoughtworks.felix.productservice.support;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CustomMatchers {
    public static Matcher<String> matchesRegex(final String regex) {
        return new BaseMatcher<String>() {
            @Override
            public boolean matches(Object item) {
                return ((String) item).matches(regex);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should match regex: " + regex);
            }
        };
    }
}
