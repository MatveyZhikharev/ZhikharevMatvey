package org.example.service.exceptions;

public class CommentCreateException extends RuntimeException {
  public CommentCreateException(String message, Throwable cause) {
    super(message, cause);
  }
}
