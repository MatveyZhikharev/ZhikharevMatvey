package repository;

import org.example.entity.Article;
import org.example.entity.id.ArticleId;

import java.util.ArrayList;

public interface ArticleRepository {
  ArticleId generateId();

  ArrayList<Article> findAll();

  Article findById(ArticleId id);

  void create(Article article);

  void update(Article article);

  void delete(ArticleId id);
}
