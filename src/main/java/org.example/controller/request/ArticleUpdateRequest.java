package org.example.controller.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entity.id.ArticleId;

import java.util.List;
import java.util.Set;

public record ArticleUpdateRequest(
    ArticleId articleId,
    String title,
    Set<String> tags,
    List<String> comments) {}
