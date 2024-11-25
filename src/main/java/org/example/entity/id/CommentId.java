package org.example.entity.id;

public class CommentId {
  private final long id;

  public CommentId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return Long.toString(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof CommentId)) {
      return false;
    }
    return id == ((CommentId) obj).id;
  }
}
