package org.example.controller;

import org.example.entity.Article;
import org.example.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleFreemarkerController implements Controller {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleFreemarkerController.class);
  private final Service service;
  private final ArticleService articleService;
  private final FreeMarkerEngine freeMarkerEngine;

  public ArticleFreemarkerController(
      Service service,
      ArticleService articleService,
      FreeMarkerEngine freeMarkerEngine
  ) {
    this.service = service;
    this.articleService = articleService;
    this.freeMarkerEngine = freeMarkerEngine;
  }

  @Override
  public void initializeEndpoints() {
    getAllArticles();
  }

  private void getAllArticles() {
    service.get("/",
        (Request request, Response response) -> {
          response.type("text/html; charset=utf-8");
          List<Article> articles = articleService.findAll();
          List<Map<String, String>>  articlesListMap =
              articles.stream()
                  .map(Article -> Map.of
                      (
                          "name", Article.getTitle() == null ? "" : Article.getTitle(),
                          "tags", Article.getTags() == null ? "" : String.join(", ", Article.getTags()),
                          "comments", Article.getComments() == null ? "0" : Integer.toString(Article.getComments().size())
                      )
                  ).toList();
          Map<String, Object> model = new HashMap<>();
          model.put("articles", articlesListMap);
          LOG.debug("Articles page is loaded");
          return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
        });
  }
}
