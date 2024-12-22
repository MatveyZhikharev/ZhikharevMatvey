package org.example.service.exceptions;

public class CommentFindException extends RuntimeException {
  public CommentFindException(String message, Throwable cause) {
    super(message, cause);
  }
}
