package com.thoughtworks.felix.priceservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SingleResource<T> {
    @JsonProperty("data")
    private T content;

    private Map<String, URI> links;

    public SingleResource(T content, Map<String, URI> links) {
        this.content = content;
        this.links = new TreeMap<>(links);
    }

    public SingleResource(T content, URI link) {
        this.content = content;
        this.links = new TreeMap<String, URI>(){{
            put("self", link);
        }};
    }

    public SingleResource(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public Map<String, URI> getLinks() {
        return Collections.unmodifiableMap(links);
    }

    private SingleResource() {}

    private void setContent(T content) {
        this.content = content;
    }

    private void setLinks(Map<String, URI> links) {
        this.links = links;
    }
}
