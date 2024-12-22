package org.example.controller.request;

import java.util.List;
import java.util.Set;

public record ArticleUpdateRequest(
    long articleId,
    String title,
    Set<String> tags,
    List<String> comments) {}
