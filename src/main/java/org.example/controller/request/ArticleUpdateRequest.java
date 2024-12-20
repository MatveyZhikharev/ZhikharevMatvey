package org.example.controller.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entity.id.ArticleId;

import java.util.List;
import java.util.Set;

public record ArticleUpdateRequest(
    @JsonProperty ArticleId articleId,
    @JsonProperty String name,
    @JsonProperty Set<String> tags,
    @JsonProperty List<String> comments) {}
