package org.example.repository.exceptions;

public class CommentDuplicateException extends RuntimeException {
  public CommentDuplicateException(String message) {
    super(message);
  }
  public CommentDuplicateException(String message, Throwable cause) {
    super(message, cause);
  }
}
