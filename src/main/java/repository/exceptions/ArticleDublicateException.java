package repository.exceptions;

public class ArticleDublicateException extends RuntimeException {
  public ArticleDublicateException(String message) {
    super(message);
  }
  public ArticleDublicateException(String message, Throwable cause) {
    super(message, cause);
  }
}
