package org.example.entity;

import org.example.entity.id.ArticleId;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Article {
  private final ArticleId id;
  private final String title;
  private final Set<String> tags;
  private final List<Comment> comments;

  public Article(ArticleId id, String title, Set<String> tags, List<Comment> comments) {
    this.id = id;
    this.title = title;
    this.tags = tags;
    this.comments = comments;
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

  public ArticleId getId() {
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
