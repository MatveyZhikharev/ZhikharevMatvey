package org.example.entity;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Article {
  private final long id;
  private final String title;
  private final Set<String> tags;
  private final List<Comment> comments;
  private final boolean trending;

  public Article(long id, String title, Set<String> tags, List<Comment> comments) {
    this.id = id;
    this.title = title;
    this.tags = tags;
    this.comments = comments;
    this.trending = comments.size() >= 3;
  }

  public Article withTags(Set<String> tags) {
    return new Article(this.id, this.title, tags, this.comments);
  }

  public Article withTitle(String title) {
    return new Article(this.id, title, this.tags, this.comments);
  }

  public Article withComments(List<Comment> comments) {
    return new Article(this.id, this.title, this.tags, comments);
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Set<String> getTags() {
    return tags;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public boolean getTrending() {
    return trending;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", tags=" + tags +
        ", comments=" + comments +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return Objects.equals(id, article.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
