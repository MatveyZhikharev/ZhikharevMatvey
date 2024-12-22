package org.example.repository;

import org.example.entity.Comment;
import org.example.repository.exceptions.CommentDuplicateException;
import org.example.repository.exceptions.CommentNotFoundException;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultIterable;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.jdbi.v3.core.statement.Update;

import java.util.*;

public class CommentRepositoryImpl implements CommentRepository {
  private final Jdbi jdbi;

  public CommentRepositoryImpl(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public long generateId() {
    Long value;

    try (Handle handle = jdbi.open()) {
      value = (Long) handle.createQuery("SELECT nextval('comment_id_seq') AS value FOR UPDATE")
          .mapToMap()
          .first()
          .get("value");
    }

    return value;
  }

  @Override
  public HashSet<Comment> findAll() {
    Set<Comment> comments;

    try (Handle handle = jdbi.open()) {
      comments = handle.createQuery("SELECT * FROM comment FOR UPDATE").mapTo(Comment.class)
          .set();
    }
    return (HashSet<Comment>) comments;
  }

  @Override
  public Long create(Comment comment) throws CommentDuplicateException {
    Long comment_id;

    try (Handle handle = jdbi.open()) {
      try (Update update = handle.createUpdate("INSERT INTO comment (id, text) VALUES (:comment_id, :text)")) {
        comment_id = (Long) update.bind("comment_id", comment.getId())
            .bind("text", comment.getText())
            .executeAndReturnGeneratedKeys("id")
            .mapToMap()
            .first()
            .get("id");
      } catch (UnableToExecuteStatementException e) {
        throw new CommentDuplicateException(e.getMessage(), e);
      }
    }

    return comment_id;
  }

  @Override
  public void delete(Long id) throws CommentNotFoundException {
    try (Handle handle = jdbi.open()) {
      try (Update update = handle.createUpdate("DELETE FROM comment WHERE id=:comment_id")) {
        update.bind("comment_id", id)
            .execute();
      } catch (UnableToExecuteStatementException e) {
        throw new CommentNotFoundException(e.getMessage(), e);
      }
    }
  }

  @Override
  public Comment findById(Long id) throws CommentNotFoundException {
    Comment comment;

    try (Handle handle = jdbi.open()) {
      ResultIterable<Comment> result = handle.createQuery("SELECT text FROM comment AS text WHERE comment_id=:comment_id FOR UPDATE")
          .bind("comment_id", id)
          .map((rs, ctx) -> new Comment(rs.getLong("comment_id"), rs.getString("text")));

      try {
        comment = result.first();
      } catch (IllegalStateException e) {
        throw new CommentNotFoundException(e.getMessage(), e);
      }
    }

    return comment;
  }

  @Override
  public void update(Comment comment) throws CommentNotFoundException {
    try (Handle handle = jdbi.open()) {
      try (Update update = handle.createUpdate("UPDATE comment SET text = :text WHERE comment_id = :comment_id")) {
        update.bind("comment_id", comment.getId())
            .bind("text", comment.getText())
            .executeAndReturnGeneratedKeys("comment_id")
            .mapToMap()
            .first()
            .get("comment_id");
      } catch (IllegalStateException e) {
        throw new CommentNotFoundException(e.getMessage(), e);
      }
    }
  }
}
