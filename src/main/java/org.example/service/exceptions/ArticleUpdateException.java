package org.example.service.exceptions;

public class ArticleUpdateException extends RuntimeException {
  public ArticleUpdateException(String message, Throwable e) {
    super(message, e);
  }
}
