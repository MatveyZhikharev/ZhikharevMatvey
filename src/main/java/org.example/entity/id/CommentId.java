package org.example.entity.id;

import java.util.Objects;

public class CommentId {
  public final long id;

  public CommentId(long id) {
    this.id = id;
  }

  public CommentId incrementAndGet() {
    return new CommentId(id + 1);
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

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
