package repository;

import org.example.entity.Article;
import org.example.entity.id.ArticleId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryArticleRepository implements ArticleRepository {
  private ArticleId nextId = new ArticleId(0);
  private final Map<Long, Article> articleMap = new ConcurrentHashMap<>();

  @Override
  public ArticleId generateId() {
    return nextId.incrementAndGet();
  }

  @Override
  public ArrayList<Article> findAll() {
    return new ArrayList<>(articleMap.values());
  }

  @Override
  public Article findById(ArticleId id) {
    return null;
  }

  @Override
  public void create(Article article) {

  }

  @Override
  public void update(Article article) {

  }

  @Override
  public void delete(ArticleId id) {

  }
}
