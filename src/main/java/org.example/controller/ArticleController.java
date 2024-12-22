package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.request.ArticleCreateRequest;
import org.example.controller.request.ArticleUpdateRequest;
import org.example.controller.response.ArticleCreateResponse;
import org.example.controller.response.ErrorResponse;
import org.example.entity.Article;
import org.example.entity.Comment;
import org.example.service.ArticleService;
import org.example.service.exceptions.ArticleCreateException;
import org.example.service.exceptions.ArticleDeleteException;
import org.example.service.exceptions.ArticleFindException;
import org.example.service.exceptions.ArticleUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.*;

public class ArticleController implements Controller {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);
  private final Service service;
  private final ArticleService articleService;
  private final ObjectMapper objectMapper;

  public ArticleController(Service service, ArticleService articleService, ObjectMapper objectMapper) {
    this.service = service;
    this.articleService = articleService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    findAll();
    findById();
    create();
    update();
    delete();
  }

  private void findAll() {
    service.get(
        "api/article/all",
        (Request request, Response response) -> {
          response.type("application/json");

          List<Article> articles = articleService.findAll();
          List<Map<String, String>> articlesList = new ArrayList<>();
          for (int i = 0; i < articles.size(); i++) {
            articlesList.add(i, new HashMap<>());
            articlesList.get(i).put("articleId", String.valueOf(articles.get(i).getId()));
            articlesList.get(i).put("name", articles.get(i).getTitle());
            articlesList.get(i).put("tags", String.join(", ", articles.get(i).getTags()));

            if (articles.get(i).getComments() != null) {
              String strComments = "";
              for (Comment comment : articles.get(i).getComments()) {
                strComments = strComments + comment.getText() + ", ";
              }
              articlesList.get(i).put("comments", strComments);
            }
          }
          LOG.info("Found {} articles", articlesList.size());
          response.status(200);
          return objectMapper.writeValueAsString(articlesList);
        }
    );
  }

  private void findById() {
    service.get("api/article/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");
          long articleId = Long.parseLong(request.params("articleId"));
          try {
            Article article = articleService.findById(articleId);
            Map<String, String> articleMap = new HashMap<>();
            articleMap.put("articleId", String.valueOf(article.getId()));
            articleMap.put("name", article.getTitle());
            articleMap.put("tags", String.join(", ", article.getTags()));
            if (article.getComments() != null) {
              String strComments = "";
              for (Comment comment : article.getComments()) {
                strComments = strComments + comment.getText() + ", ";
              }
              articleMap.put("comments", strComments);
            }
            response.status(200);
            return objectMapper.writeValueAsString(articleMap);
          } catch (ArticleFindException e) {
            LOG.warn("Not found article Id: {} and exception {}", articleId, e.getMessage());
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  private void create() {
    service.post("/api/article",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          ArticleCreateRequest articleCreateRequest = objectMapper.readValue(body, ArticleCreateRequest.class);
          try {
            long articleId = articleService.create(articleCreateRequest.title(), articleCreateRequest.tags());
            LOG.debug("Article created successfully: {}", articleId);
            response.status(201);
            return objectMapper.writeValueAsString(new ArticleCreateResponse(articleId));
          } catch (ArticleCreateException e) {
            LOG.warn("ArticleCreateException on articleService.create()", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  private void update() {
    service.post("api/article/update/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");
          long articleId = Long.parseLong(request.params("articleId"));
          ArticleUpdateRequest articleUpdateRequest = objectMapper.readValue(request.body(), ArticleUpdateRequest.class);
          try {
            List<Comment> commentList = new ArrayList<>();
            int comId = 0;
            for (String comment : articleUpdateRequest.comments()) {
              commentList.add(new Comment(comId, comment));
              comId++;
            }
            articleService.update(
                articleId,
                articleUpdateRequest.title(),
                articleUpdateRequest.tags(),
                commentList
            );
            response.status(200);
            return objectMapper.writeValueAsString(articleId);
          } catch (ArticleUpdateException e) {
            LOG.warn("Not found article Id: {} and exception", articleId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  public void delete() {
    service.delete("api/article/delete/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");
          long articleId = Long.parseLong(request.params("articleId"));
          try {
            articleService.delete(
                articleId
            );
            response.status(200);
            return objectMapper.writeValueAsString(articleId);
          } catch (ArticleDeleteException e) {
            LOG.warn("Not found article Id: {} and exception", articleId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }
}
