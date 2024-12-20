package org.example.service.exceptions;

public class CommentDeleteException extends RuntimeException {
  public CommentDeleteException(String message, Throwable cause) {
    super(message, cause);
  }
}
