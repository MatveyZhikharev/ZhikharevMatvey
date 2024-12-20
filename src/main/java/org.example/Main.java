package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.ArticleController;
import org.example.controller.ArticleFreemarkerController;
import org.example.controller.CommentController;
import org.example.repository.InMemoryArticleRepository;
import org.example.repository.InMemoryCommentRepository;
import org.example.service.ArticleService;
import org.example.service.CommentService;
import org.example.template.TemplateFactory;
import spark.Service;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new ArticleController(
                service,
                new ArticleService(
                    new InMemoryArticleRepository()
                ),
                objectMapper
            ),
            new ArticleFreemarkerController(
                service,
                new ArticleService(
                    new InMemoryArticleRepository()
                ),
                TemplateFactory.freeMarkerEngine()
            ),
            new CommentController(
                service,
                new CommentService(
                    new InMemoryCommentRepository()
                ),
                objectMapper
            )
        )
    );
    application.start();
  }
}