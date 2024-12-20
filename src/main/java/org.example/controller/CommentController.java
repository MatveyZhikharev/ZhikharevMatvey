package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.request.ArticleCreateRequest;
import org.example.controller.request.ArticleUpdateRequest;
import org.example.controller.request.CommentCreateRequest;
import org.example.controller.request.CommentUpdateRequest;
import org.example.controller.response.CommentCreateResponse;
import org.example.controller.response.ErrorResponse;
import org.example.entity.Article;
import org.example.entity.Comment;
import org.example.entity.id.ArticleId;
import org.example.entity.id.CommentId;
import org.example.service.CommentService;
import org.example.service.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Service;

import java.util.*;

public class CommentController implements Controller {
  private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);
  private final Service service;
  private final CommentService commentService;
  private final ObjectMapper objectMapper;

  public CommentController(Service service, CommentService commentService, ObjectMapper objectMapper) {
    this.service = service;
    this.commentService = commentService;
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
        "api/comment/all",
        (Request request, Response response) -> {
          response.type("application/json");

          HashSet<Comment> comments = commentService.findAll();
          List<Map<String, String>> commentList = new ArrayList<>();
          int i = 0;
          for (Comment comment : comments) {
            commentList.add(i, new HashMap<>());
            commentList.get(i).put("commentId", String.valueOf(comment.getId()));
            commentList.get(i).put("articleId", String.valueOf(comment.getArticleId()));
            commentList.get(i).put("text", comment.getText());
          }
          LOG.info("Found {} comments", commentList.size());
          response.status(200);
          return objectMapper.writeValueAsString(commentList);
        }
    );
  }

  private void findById() {
    service.get("api/comment/:commentId",
        (Request request, Response response) -> {
          response.type("application/json");
          CommentId commentId = new CommentId(Long.parseLong(request.params("commentId")));
          try {
            Comment comment = commentService.findById(commentId);
            Map<String, String> commentMap = new HashMap<>();
            commentMap.put("commentId", String.valueOf(comment.getId()));
            commentMap.put("articleId", String.valueOf(comment.getArticleId()));
            commentMap.put("text", comment.getText());

            response.status(200);
            return objectMapper.writeValueAsString(commentMap);
          } catch (CommentFindException e) {
            LOG.warn("Not found comment Id: {} and exception {}", commentId, e.getMessage());
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  private void create() {
    service.post("/api/comment",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          CommentCreateRequest commentCreateRequest = objectMapper.readValue(body, CommentCreateRequest.class);
          try {
            CommentId commentId = commentService.create(commentCreateRequest.articleId(), commentCreateRequest.text());
            LOG.debug("Comment created successfully: {}", commentId);
            response.status(201);
            return objectMapper.writeValueAsString(new CommentCreateResponse(commentId));
          } catch (CommentCreateException e) {
            LOG.warn("CommentCreateException on commentService.create()", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  private void update() {
    service.post("api/comment/update/:commentId",
        (Request request, Response response) -> {
          response.type("application/json");
          CommentId commentId = new CommentId(Long.parseLong(request.params("commentId")));
          CommentUpdateRequest commentUpdateRequest = objectMapper.readValue(request.body(), CommentUpdateRequest.class);
          try {
            commentService.update(
                commentId,
                commentUpdateRequest.text()
            );
            response.status(200);
            return objectMapper.writeValueAsString(commentId);
          } catch (ArticleUpdateException e) {
            LOG.warn("Not found comment Id: {} and exception", commentId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }

  public void delete() {
    service.delete("api/comment/delete/:commentId",
        (Request request, Response response) -> {
          response.type("application/json");
          CommentId commentId = new CommentId(Long.parseLong(request.params("commentId")));
          try {
            commentService.delete(
                commentId
            );
            response.status(200);
            return objectMapper.writeValueAsString(commentId);
          } catch (CommentDeleteException e) {
            LOG.warn("Not found comment Id: {} and exception", commentId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        });
  }
}
