package org.example.controller.request;

public record CommentCreateRequest(
    long articleId,
    String text) {
}
