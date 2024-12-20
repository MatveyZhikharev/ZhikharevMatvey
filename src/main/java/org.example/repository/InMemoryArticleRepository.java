package org.example.repository;

import org.example.entity.Article;
import org.example.entity.id.ArticleId;
import org.example.repository.exceptions.ArticleDuplicateException;
import org.example.repository.exceptions.ArticleNotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryArticleRepository implements ArticleRepository {
  private ArticleId nextId = new ArticleId(0);
  private final Map<ArticleId, Article> articleMap = new ConcurrentHashMap<>();

  @Override
  public ArticleId generateId() {
    nextId = nextId.incrementAndGet();
    return nextId;

  }

  @Override
  public ArrayList<Article> findAll() {
    return new ArrayList<>(articleMap.values());
  }

  @Override
  public Article findById(ArticleId id) {
    Article article = articleMap.get(id);
    if (article == null) {
      throw new ArticleNotFoundException("Cannot find an article with ID== " + id);
    }
    return article;
  }

  @Override
  public void create(Article article) {
    if (articleMap.get(article.getId()) != null) {
      throw new ArticleDuplicateException("Article with ID=" + article.getId() + " already exists");
    }
    articleMap.put(article.getId(), article);
  }

  @Override
  public void update(Article article) {
    if (articleMap.get(article.getId()) == null) {
      throw new ArticleNotFoundException("Article with ID=" + article.getId() + " not found");
    }
    articleMap.put(article.getId(), article);
  }

  @Override
  public void delete(ArticleId id) {
    if (articleMap.remove(id) == null) {
      throw new ArticleNotFoundException("Article with ID=" + id + " can not be deleted (not found)");
    }
    articleMap.remove(id);
  }
}
