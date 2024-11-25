package repository;

import org.example.entity.Article;
import org.example.entity.id.ArticleId;
import repository.exceptions.ArticleNotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryArticleRepository implements ArticleRepository {
  private ArticleId nextId = new ArticleId(0);
  private final Map<ArticleId, Article> articleMap = new ConcurrentHashMap<>();

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
    Article article = articleMap.get(id);
    if (article == null) {
      throw new ArticleNotFoundException("Cannot find a book with id = " + id);
    }
    return article;
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
