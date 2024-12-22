import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.example.Application;
import org.example.controller.ArticleController;
import org.example.controller.ArticleFreemarkerController;
import org.example.controller.CommentController;
import org.example.repository.ArticleRepository;
import org.example.repository.ArticleRepositoryImpl;
import org.example.repository.CommentRepository;
import org.example.repository.CommentRepositoryImpl;
import org.example.service.ArticleService;
import org.example.service.CommentService;
import org.example.template.TemplateFactory;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

class E2ETest {
  private Service service;


  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void FullTest() throws Exception {
    Config config = ConfigFactory.load();

    Jdbi jdbi = Jdbi.create(config.getString("app.database.url"), config.getString("app.database.user"),
        config.getString("app.database.password"));

    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new ArticleRepositoryImpl(jdbi);
    final CommentRepository commentRepository = new CommentRepositoryImpl(jdbi);
    final CommentService commentService = new CommentService(commentRepository);
    final ArticleService articleService = new ArticleService(articleRepository);

    Application application = new Application(
        List.of(
            new ArticleController(
                service,
                articleService,
                objectMapper
            ),
            new CommentController(
                service,
                commentService,
                objectMapper
            ),
            new ArticleFreemarkerController(
                service,
                articleService,
                TemplateFactory.freeMarkerEngine()
            )
        )
    );
    application.start();
    service.awaitInitialization();


    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "title": "Test", "tags": ["first", "second", "third"] }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, response.statusCode());

    HttpResponse<String> responseCommentCreate = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "articleId": "1", "text": "GOYDA" }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comment".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, responseCommentCreate.statusCode());

    HttpResponse<String> responseForGetAllArticle = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:%d/api/article/all".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(201, response.statusCode());

    HttpResponse<String> responseGetArticleById = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:%d/api/article/1".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, responseGetArticleById.statusCode());

    HttpResponse<String> responseForUpdateArticle = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "title": "Test", "tags": ["first", "second", "third"], "comments" : ["commentFirst", "commentSecond"] }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article/update/1".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, responseForUpdateArticle.statusCode());

    HttpResponse<String> responseForDeleteComment = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:%d/api/comment/delete/1".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, responseForDeleteComment.statusCode());

    HttpResponse<String> responseForDeleteArticle = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:%d/api/article/1".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, responseForDeleteComment.statusCode());

  }
}
