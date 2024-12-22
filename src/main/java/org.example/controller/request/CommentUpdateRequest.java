package org.example.controller.request;

public record CommentUpdateRequest(
    long commentId,
    String text) {}
