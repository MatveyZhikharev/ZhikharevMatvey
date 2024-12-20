package org.example.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entity.id.ArticleId;
import org.example.entity.id.CommentId;

public record CommentUpdateRequest(
    @JsonProperty CommentId commentId,
    @JsonProperty String text) {}
