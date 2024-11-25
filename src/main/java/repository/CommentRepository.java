package repository;

import org.example.entity.Comment;
import org.example.entity.id.CommentId;

import java.util.Set;

public interface CommentRepository {
  CommentId generateId();

  Set<String> findAll();
  Comment findById(CommentId id);

  void create(Comment article);

  void update(Comment article);

  void delete(CommentId id);
}
