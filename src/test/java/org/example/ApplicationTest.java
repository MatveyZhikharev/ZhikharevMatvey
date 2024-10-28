package org.example;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ApplicationTest extends TestCase {
  @Test
  void enrich() {
    UserRepository repository = new MapUserRepository();
    repository.updateUserByMsisdn("89528120000",
        new User("User1", "User1ov"));
    repository.updateUserByMsisdn("88005553535",
        new User("User2", "User2ov"));

    EnrichmentService service = new EnrichmentService();
    service.addEnrichment(Message.EnrichmentType.MSISDN, new MsisdnEnrichment(repository));

    Message message1 = new Message(
        Map.of("message", "adekdot", "msisdn", "88005553535"),
        Message.EnrichmentType.MSISDN);

    Message expected1 = new Message(Map.of(
        "message", "adekdot",
        "msisdn", "88005553535",
        "firstName", "User2",
        "lastName", "User2ov"
    ), Message.EnrichmentType.MSISDN);
    Message actual1 = service.enrich(message1);

    assertEquals(expected1, actual1);

    Message message2 = new Message(
        Map.of("message", "xaxaxa", "msisdn", "89528120000"),
        Message.EnrichmentType.MSISDN);

    Message expected2 = new Message(Map.of(
        "message", "xaxaxa",
        "msisdn", "89528120000",
        "firstName", "User1",
        "lastName", "User1ov"
    ), Message.EnrichmentType.MSISDN);
    Message actual2 = service.enrich(message2);

    assertEquals(expected2, actual2);
  }

  @Test
  void enrichConcurrent() throws InterruptedException, ExecutionException {
    UserRepository repository = new MapUserRepository();
    repository.updateUserByMsisdn("89528120000",
        new User("User1", "User1ov"));
    repository.updateUserByMsisdn("88005553535",
        new User("User2", "User2ov"));

    EnrichmentService service = new EnrichmentService();
    service.addEnrichment(Message.EnrichmentType.MSISDN, new MsisdnEnrichment(repository));

    List<Map<String, String>> contents = new CopyOnWriteArrayList<>(List.of(
        Map.of("message", "text1", "msisdn", "89528120000"),
        Map.of("message", "text2", "msisdn", "88005553535"),
        Map.of("message", "text3", "msisdn", "88005553535"),
        Map.of("message", "text4", "msisdn", "89528120000"),
        Map.of("message", "text5")
    ));

    List<Message> expected = List.of(
        new Message(new ConcurrentHashMap<>(Map.of(
            "message", "text1",
            "msisdn", "89528120000",
            "firstName", "User1",
            "lastName", "User1ov")), Message.EnrichmentType.MSISDN),
        new Message(new ConcurrentHashMap<>(Map.of(
            "message", "text2",
            "msisdn", "88005553535",
            "firstName", "User2",
            "lastName", "User2ov")), Message.EnrichmentType.MSISDN),
        new Message(new ConcurrentHashMap<>(Map.of(
            "message", "text3",
            "msisdn", "88005553535",
            "firstName", "User2",
            "lastName", "User2ov")), Message.EnrichmentType.MSISDN),
        new Message(new ConcurrentHashMap<>(Map.of(
            "message", "text4",
            "msisdn", "89528120000",
            "firstName", "User1",
            "lastName", "User1ov")), Message.EnrichmentType.MSISDN),
        new Message(new ConcurrentHashMap<>(Map.of("message", "text5")),
            Message.EnrichmentType.MSISDN)
    );

    List<Message> actual = new CopyOnWriteArrayList<>();
    for (int i = 0; i < 5; ++i) {
      actual.add(i, new Message(null, null));
    }

    final ExecutorService executorService = Executors.newFixedThreadPool(5);

    ArrayList<Future<?>> futures = new ArrayList<>(5);
    for (int i = 0; i < 5; i++) {
      final int fi = i;
      futures.add(executorService.submit(() -> {
        Message message = new Message(contents.get(fi),
            Message.EnrichmentType.MSISDN);

        actual.set(fi, service.enrich(message));
      }));
    }

    for (Future<?> future : futures) {
      future.get();
    }

    assertEquals(expected, actual);
  }
}