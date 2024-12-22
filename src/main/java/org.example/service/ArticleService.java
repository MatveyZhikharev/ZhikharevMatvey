package org.example.service;

import org.example.entity.Comment;
import org.example.repository.exceptions.ArticleDuplicateException;
import org.example.service.exceptions.ArticleCreateException;
import org.example.service.exceptions.ArticleDeleteException;
import org.example.service.exceptions.ArticleFindException;
import org.example.entity.Article;
import org.example.repository.ArticleRepository;
import org.example.repository.exceptions.ArticleNotFoundException;
import org.example.service.exceptions.ArticleUpdateException;

import java.util.List;
import java.util.Set;

public class ArticleService {
  private final ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public List<Article> findAll() {
    return articleRepository.findAll();
  }

  public Article findById(Long id) throws ArticleFindException {
    try {
      return articleRepository.findById(id);
    } catch (ArticleNotFoundException e) {
      throw new ArticleFindException("Article with ID=" + id + " not found", e);
    }
  }

  public void delete(long id) throws ArticleFindException {
    try {
      articleRepository.delete(id);
    } catch (ArticleNotFoundException e) {
      throw new ArticleDeleteException("Article with ID=" + id + " can not be deleted (not found)", e);
    }
  }

  public long create(String title, Set<String> tags) throws ArticleCreateException {
    long articleId = articleRepository.generateId().get();
    Article article = new Article(articleId, title, tags, null);
    try {
      articleRepository.create(article);
    } catch (ArticleDuplicateException e) { // маловероятная ошибка мы же генерим уникальные id, хз почему она в примере
      throw new ArticleCreateException("Article with ID=" + articleId + " already exists", e);
    }
    return articleId;
  }

  public void update(long articleId, String title, Set<String> tags, List<Comment> comments) throws ArticleUpdateException {
    Article article;
    try {
      article = articleRepository.findById(articleId);
      articleRepository.update(
          article
              .withTitle(title)
              .withTags(tags)
              .withComments(comments)
      );
    } catch (ArticleNotFoundException e) {
      throw new ArticleUpdateException("Article with ID=" + articleId + " can not be updated (not found)", e);
    }
  }
}
