package org.example.repository.exceptions;

public class CommentNotFoundException extends RuntimeException {
  public CommentNotFoundException(String message, Throwable e) {
    super(message, e);
  }
}
