package org.example.repository;

import org.example.entity.Article;
import org.example.entity.Comment;
import org.example.entity.id.CommentId;
import org.example.repository.exceptions.ArticleDuplicateException;
import org.example.repository.exceptions.ArticleNotFoundException;
import org.example.repository.exceptions.CommentDuplicateException;
import org.example.repository.exceptions.CommentNotFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCommentRepository implements CommentRepository {
  private CommentId nextId = new CommentId(0);
  private final Map<CommentId, Comment> commentMap = new ConcurrentHashMap<>();


  @Override
  public CommentId generateId() {
    nextId = nextId.incrementAndGet();
    return nextId;
  }

  @Override
  public HashSet<Comment> findAll() {
    return new HashSet<>(commentMap.values());
  }

  @Override
  public Comment findById(CommentId id) {
    Comment comment = commentMap.get(id);
    if (comment == null) {
      throw new CommentNotFoundException("Cannot find an comment with ID== " + id);
    }
    return comment;
  }

  @Override
  public void create(Comment comment) {
    if (commentMap.get(comment.getId()) != null) {
      throw new CommentDuplicateException("Comment with ID=" + comment.getId() + " already exists");
    }
    commentMap.put(comment.getId(), comment);
  }

  @Override
  public void update(Comment comment) {
    if (commentMap.get(comment.getId()) == null) {
      throw new CommentNotFoundException("Comment with ID=" + comment.getId() + " not found");
    }
    commentMap.put(comment.getId(), comment);
  }

  @Override
  public void delete(CommentId id) {
    if (commentMap.remove(id) == null) {
      throw new CommentNotFoundException("Comment with ID=" + id + " can not be deleted (not found)");
    }
    commentMap.remove(id);
  }
}
