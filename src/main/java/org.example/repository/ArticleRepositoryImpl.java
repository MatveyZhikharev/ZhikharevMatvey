package org.example.repository;

import org.example.entity.Article;
import org.example.entity.Comment;
import org.example.repository.exceptions.ArticleDuplicateException;
import org.example.repository.exceptions.ArticleNotFoundException;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultIterable;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.jdbi.v3.core.statement.Update;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArticleRepositoryImpl implements ArticleRepository {
  private final Jdbi jdbi;

  public ArticleRepositoryImpl(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public Optional<Long> generateId() {
    Optional<Long> value;

    try (Handle handle = jdbi.open()) {
      value = Optional.ofNullable((Long) handle.createQuery("SELECT nextval('article_id_seq') AS value FOR UPDATE")
          .mapToMap()
          .first()
          .get("value"));
    }

    return value;
  }

  @Override
  public List<Article> findAll() {
    List<Article> articles;

    try (Handle handle = jdbi.open()) {
      articles = handle.createQuery("SELECT * FROM article FOR UPDATE")
          .map((rs, ctx) ->
              new Article(
                  rs.getLong("id"),
                  rs.getString("title"),
                  Stream.of((String[]) rs.getArray("tags").getArray())
                      .collect(Collectors.toSet()),
                  rs.getArray("comments_id") == null ? new ArrayList<>() :
                      Stream.of((Long[]) rs.getArray("comments_id").getArray())
                          .map(x -> new Comment(x.longValue(), (String) handle.createQuery("SELECT * FROM comment WHERE id=:comment_id FOR UPDATE")
                              .bind("comment_id", x)
                              .mapToMap()
                              .first()
                              .get("text"))
                          )
                          .collect(Collectors.toList())))
          .list();
    }

    return articles;
  }

  @Override
  public Article findById(Long id) throws ArticleNotFoundException {
    Article article;

    try (Handle handle = jdbi.open()) {
      ResultIterable<Article> result = handle.createQuery("SELECT * FROM article WHERE id=:article_id FOR UPDATE")
          .bind("article_id", id)
          .map((rs, ctx) -> new Article(
                  rs.getLong("id"),
                  rs.getString("title"),
                  Stream.of((String[]) rs.getArray("tags").getArray())
                      .collect(Collectors.toSet()),
                  Stream.of((Long[]) rs.getArray("comments_id").getArray())
                          .map(x -> new Comment(x.longValue(), (String) handle.createQuery("SELECT text FROM comment AS text WHERE comment_id = :comment_id FOR UPDATE")
                              .bind("comment_id", x)
                              .mapToMap()
                              .first()
                              .get("text"))
                          )
                          .collect(Collectors.toList()))
              );

      try {
        article = result.first();
      } catch (IllegalStateException e) {
        throw new ArticleNotFoundException(e.getMessage(), e);
      }
    }

    return article;
  }

  @Override
  public Long create(Article article) throws ArticleDuplicateException {
    Long article_id;

    try (Handle handle = jdbi.open()) {
      try (Update update = handle.createUpdate("INSERT INTO article (id, title, tags, comments_id, trending) VALUES (:article_id, :title, :tags, :comments_id, :trending)")) {
        article_id = (Long) update.bind("article_id", article.getId())
            .bind("title", article.getTitle())
            .bindArray("tags", String.class, article.getTags())
            .bindArray("comments_id", Long.class,
                article.getComments() == null ? new ArrayList<Long>() : article.getComments())
            .bind("trending", article.getTrending())
            .executeAndReturnGeneratedKeys("id")
            .mapToMap()
            .first()
            .get("id");
      } catch (UnableToExecuteStatementException e) {
        throw new ArticleDuplicateException(e.getMessage(), e);
      }
    }

    return article_id;
  }

  @Override
  public void update(Article article) throws ArticleNotFoundException {
    try (Handle handle = jdbi.open()) {
      try (Update update = handle.createUpdate("UPDATE article SET title=:title, tags=:tags, comments_id=:comments_id, trending=:trending WHERE id=:article_id")) {
        update.bind("article_id", article.getId())
            .bind("title", article.getTitle())
            .bindArray("tags", String.class, article.getTags())
            .bindArray("comments_id", Long.class,
                article.getComments() == null ? new ArrayList<Long>() : article.getComments())
            .bind("trending", article.getTrending())
            .executeAndReturnGeneratedKeys("id")
            .mapToMap()
            .first()
            .get("id");
      } catch (IllegalStateException e) {
        throw new ArticleNotFoundException(e.getMessage(), e);
      }
    }
  }

  @Override
  public void delete(Long id) throws ArticleNotFoundException {
    Long article_id;

    try (Handle handle = jdbi.open()) {
      try (Update update = handle.createUpdate("DELETE FROM article WHERE id=:article_id")) {
        article_id = (Long) update.bind("article_id", id)
            .executeAndReturnGeneratedKeys("id")
            .mapToMap()
            .first()
            .get("id");
      } catch (IllegalStateException e) {
        throw new ArticleNotFoundException(e.getMessage(), e);
      }
    }
  }
}
