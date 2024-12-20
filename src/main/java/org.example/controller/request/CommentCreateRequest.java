package org.example.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entity.id.ArticleId;

public record CommentCreateRequest(
    @JsonProperty ArticleId articleId,
    @JsonProperty String text) {
}
