package org.example.repository;

import org.example.entity.Comment;
import org.example.entity.id.CommentId;

import java.util.HashSet;

public interface CommentRepository {
  CommentId generateId();

  HashSet<Comment> findAll();
  Comment findById(CommentId id);

  void create(Comment comment);

  void update(Comment comment);

  void delete(CommentId id);
}
