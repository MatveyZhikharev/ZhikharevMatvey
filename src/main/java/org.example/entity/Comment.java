package org.example.entity;

public class Comment {
  private final long id;
  private final String text;

  public Comment(long id, String text) {
    this.id = id;
    this.text = text;
  }

  public Comment withText(String text) {
    return new Comment(this.id, text);
  }

  public String getText() {
    return text;
  }

  public long getId() {
    return id;
  }
}
