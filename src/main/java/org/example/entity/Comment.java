package org.example.entity;

import org.example.entity.id.ArticleId;
import org.example.entity.id.CommentId;

public class Comment {
  private final CommentId id;
  private final ArticleId articleId;
  private final String text;

  public Comment(CommentId id, ArticleId articleId, String text) {
    this.id = id;
    this.articleId = articleId;
    this.text = text;
  }

  public Comment withText(String text) {
    return new Comment(this.id, this.articleId, text);
  }
}
