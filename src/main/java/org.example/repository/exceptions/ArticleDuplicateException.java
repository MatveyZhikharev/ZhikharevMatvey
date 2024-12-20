package org.example.repository.exceptions;

public class ArticleDuplicateException extends RuntimeException {
  public ArticleDuplicateException(String message) {
    super(message);
  }
  public ArticleDuplicateException(String message, Throwable cause) {
    super(message, cause);
  }
}
