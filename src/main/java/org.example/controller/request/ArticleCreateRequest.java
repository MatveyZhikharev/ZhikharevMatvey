package org.example.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record ArticleCreateRequest(
    @JsonProperty String title,
    @JsonProperty Set<String> tags) {
}
