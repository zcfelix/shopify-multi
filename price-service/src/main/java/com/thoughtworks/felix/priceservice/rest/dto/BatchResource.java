package com.thoughtworks.felix.priceservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BatchResource<T> {

    @JsonProperty("data")
    private List<T> contents;

    private Map<String, URI> links;

    public BatchResource(List<T> contents) {
        this.contents = contents;
    }

    public BatchResource(List<T> contents, Map<String, URI> links) {
        this.contents = contents;
        this.links = new TreeMap<>(links);
    }

    public BatchResource(List<T> contents, URI link) {
        this.contents = contents;
        this.links = new TreeMap<String, URI>() {{
            put("self", link);
        }};
    }

    public List<T> getContents() {
        return contents;
    }

    public Map<String, URI> getLinks() {
        return Collections.unmodifiableMap(links);
    }

    private BatchResource() {
    }

    private void setContents(List<T> contents) {
        this.contents = contents;
    }

    private void setLinks(Map<String, URI> links) {
        this.links = links;
    }
}
