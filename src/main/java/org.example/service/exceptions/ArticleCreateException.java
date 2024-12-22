package org.example.service.exceptions;

public class ArticleCreateException extends RuntimeException {
  public ArticleCreateException(String message, Throwable cause) {
    super(message, cause);
  }
}
