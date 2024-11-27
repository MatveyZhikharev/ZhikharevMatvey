package repository;

import org.example.entity.Comment;
import org.example.entity.id.CommentId;

import java.util.Map;
import java.util.Set;
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
  public Set<String> findAll() {
    return Set.of();
  }

  @Override
  public Comment findById(CommentId id) {
    return null;
  }

  @Override
  public void create(Comment article) {

  }

  @Override
  public void update(Comment article) {

  }

  @Override
  public void delete(CommentId id) {

  }
}
