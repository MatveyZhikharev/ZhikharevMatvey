package org.example.repository;

import org.example.entity.Comment;
import org.example.repository.exceptions.CommentDuplicateException;
import org.example.repository.exceptions.CommentNotFoundException;

import java.util.HashSet;

public interface CommentRepository {
  long generateId();

  HashSet<Comment> findAll();
  Comment findById(Long id) throws CommentNotFoundException;

  Long create(Comment comment) throws CommentDuplicateException;

  void update(Comment comment) throws CommentNotFoundException;

  void delete(Long id) throws CommentNotFoundException;
}
