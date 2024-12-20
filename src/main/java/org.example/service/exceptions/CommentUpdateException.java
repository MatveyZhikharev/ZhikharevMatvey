package org.example.service.exceptions;

public class CommentUpdateException extends RuntimeException {
  public CommentUpdateException(String message, Throwable cause) {
    super(message, cause);
  }
}
