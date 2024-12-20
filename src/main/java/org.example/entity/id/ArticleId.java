package org.example.entity.id;

public class ArticleId {
  private final long id;

  public ArticleId(long id) {
    this.id = id;
  }

  public ArticleId incrementAndGet() {
    return new ArticleId(id + 1);
  }

  @Override
  public String toString() {
    return Long.toString(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ArticleId)) {
      return false;
    }
    return id == ((ArticleId) obj).id;
  }
}
