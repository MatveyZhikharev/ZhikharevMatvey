package org.example.repository;

import org.example.entity.Article;
import org.example.repository.exceptions.ArticleDuplicateException;
import org.example.repository.exceptions.ArticleNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
  Optional<Long> generateId();

  List<Article> findAll();

  Article findById(Long id) throws ArticleNotFoundException;

  Long create(Article article) throws ArticleDuplicateException;

  void update(Article article) throws ArticleNotFoundException;

  void delete(Long id) throws ArticleNotFoundException;
}
