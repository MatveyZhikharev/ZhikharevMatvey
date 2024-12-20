
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Application;
import org.example.controller.ArticleController;
import org.example.controller.ArticleFreemarkerController;
import org.example.controller.CommentController;
import org.example.repository.ArticleRepository;
import org.example.repository.CommentRepository;
import org.example.repository.InMemoryArticleRepository;
import org.example.repository.InMemoryCommentRepository;
import org.example.service.ArticleService;
import org.example.service.CommentService;
import org.example.template.TemplateFactory;
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

class ApplicationTest {
  private Service service;

  @BeforeEach
  void befofeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void should201IfArticleIsSuccessfullyCreated() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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
  }

  @Test
  void should201IfArticleIsSuccessfullyUpdated() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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

    HttpResponse<String> nextResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            {
                            "title": "newTest",
                            "tags": [
                              "newFirst",
                              "newSecond",
                              "newThird"
                            ],
                            "comments": [
                              "java",
                              "python"
                            ]
                            }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article/update/1".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, nextResponse.statusCode());
  }

  @Test
  void should404IfArticleIdIsNotFound() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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

    HttpResponse<String> nextResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:%d/api/article/999".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(404, nextResponse.statusCode());
  }

  @Test
  void should404IfArticleUpdateIdIsNotFound() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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

    HttpResponse<String> nextResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "title": "NewTest", "tags": ["first2", "second2", "third2"], "comments" : ["java", "python"] }"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/article/update/666".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(404, nextResponse.statusCode());
  }

  @Test
  void should200IfArticleFindAll() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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

    HttpResponse<String> newResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:%d/api/article/all".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, newResponse.statusCode());
  }

  @Test
  void should201CommentCreateById() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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
    HttpResponse<String> newResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "articleId": 1, "text": "GOYDAAAA"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comment".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(201, newResponse.statusCode());
  }
  @Test
  void should201CommentUpdate() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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
    HttpResponse<String> commentResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "articleId": 1, "text": "GOYDAAAA"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comment".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(201, commentResponse.statusCode());

    HttpResponse<String> newResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "articleId": 1, "text": "GOYDAAAA"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comment".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(201, newResponse.statusCode());
  }

  @Test
  void should201CommentDelete() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    final ArticleRepository articleRepository = new InMemoryArticleRepository();
    final CommentRepository commentRepository = new InMemoryCommentRepository();
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

    HttpResponse<String> newResponseForComment = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .POST(
                    HttpRequest.BodyPublishers.ofString(
                        """
                            { "articleId" : "1", "text" : "GOYDAAA"}"""
                    )
                )
                .uri(URI.create("http://localhost:%d/api/comment".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );


    HttpResponse<String> newResponse = HttpClient.newHttpClient()
        .send(
            HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:%d/api/comment/delete/1".formatted(service.port())))
                .build(),
            HttpResponse.BodyHandlers.ofString(UTF_8)
        );
    assertEquals(200, newResponse.statusCode());
  }
}