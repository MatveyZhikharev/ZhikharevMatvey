package org.example.service;

import org.example.entity.Comment;
import org.example.repository.CommentRepository;
import org.example.repository.exceptions.CommentDuplicateException;
import org.example.repository.exceptions.CommentNotFoundException;
import org.example.service.exceptions.CommentCreateException;
import org.example.service.exceptions.CommentDeleteException;
import org.example.service.exceptions.CommentFindException;
import org.example.service.exceptions.CommentUpdateException;

import java.util.HashSet;


public class CommentService {
  private final CommentRepository commentRepository;

  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public HashSet<Comment> findAll() {
    return commentRepository.findAll();
  }

  public Comment findById(long id) throws CommentFindException {
    try {
      return commentRepository.findById(id);
    } catch (CommentNotFoundException e) {
      throw new CommentFindException("Comment with ID=" + id + " not found", e);
    }
  }

  public void delete(long id) throws CommentFindException {
    try {
      commentRepository.delete(id);
    } catch (CommentNotFoundException e) {
      throw new CommentDeleteException("Comment with ID=" + id + " can not be deleted (not found)", e);
    }
  }

  public long create(long articleId, String text) throws CommentCreateException {
    long commentId = commentRepository.generateId();
    Comment comment = new Comment(commentId, text);
    try {
      commentRepository.create(comment);
    } catch (CommentDuplicateException e) { // маловероятная ошибка мы же генерим уникальные id, хз почему она в примере
      throw new CommentCreateException("Comment with ID=" + commentId + " already exists", e);
    }
    return commentId;
  }

  public void update(long commentId, String text) throws CommentUpdateException {
    Comment comment;
    try {
      comment = commentRepository.findById(commentId);
      commentRepository.update(
          comment
              .withText(text)
      );
    } catch (CommentNotFoundException e) {
      throw new CommentUpdateException("Comment with ID=" + commentId + " can not be updated (not found)", e);
    }
  }
}
