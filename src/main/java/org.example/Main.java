package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.ArticleController;
import org.example.controller.ArticleFreemarkerController;
import org.example.controller.CommentController;
import org.example.repository.ArticleRepositoryImpl;
import org.example.repository.CommentRepositoryImpl;
import org.example.service.ArticleService;
import org.example.service.CommentService;
import org.example.template.TemplateFactory;
import org.jdbi.v3.core.Jdbi;
import spark.Service;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    Config config = ConfigFactory.load();

    Flyway flyway =
        Flyway.configure()
            .outOfOrder(true)
            .locations("classpath:db/migrations")
            .dataSource(config.getString("app.database.url"), config.getString("app.database.user"),
                config.getString("app.database.password"))
            .load();
    flyway.migrate();

    Jdbi jdbi = Jdbi.create(config.getString("app.database.url"), config.getString("app.database.user"),
        config.getString("app.database.password"));

    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new ArticleController(
                service,
                new ArticleService(
                    new ArticleRepositoryImpl(jdbi)
                ),
                objectMapper
            ),
            new ArticleFreemarkerController(
                service,
                new ArticleService(
                    new ArticleRepositoryImpl(jdbi)
                ),
                TemplateFactory.freeMarkerEngine()
            ),
            new CommentController(
                service,
                new CommentService(
                    new CommentRepositoryImpl(jdbi)
                ),
                objectMapper
            )
        )
    );
    application.start();
  }
}