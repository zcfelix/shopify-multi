package com.thoughtworks.felix.priceservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDTO {

    @JsonProperty
    private String field;

    @JsonProperty("rejected_value")
    private String rejectedValue;

    @JsonProperty
    private String message;

    @JsonCreator
    private ErrorDTO() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ErrorDTO errorDTO = new ErrorDTO();

        public Builder withField(String field) {
            errorDTO.field = field;
            return this;
        }

        public Builder withRejectedValue(String rejectedValue) {
            errorDTO.rejectedValue = rejectedValue;
            return this;
        }

        public Builder withMessage(String message) {
            errorDTO.message = message;
            return this;
        }

        public ErrorDTO build() {
            return errorDTO;
        }
    }
}
