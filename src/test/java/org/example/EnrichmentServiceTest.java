package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class EnrichmentServiceTest {
  @Test
  void enrich() {
    final EnrichmentService service = new EnrichmentService();
    service.addEnrichment(Message.EnrichmentType.MSISDN, new Enrichment() {
      @Override
      public Map<String, String> enrich(Map<String, String> content) {
        content.put("first_name", "Username");
        content.put("last_name", "Usernameov");
        content.put("msisdn", "89528120000");
        return content;
      }
    });

    MapUserRepository users = new MapUserRepository();
    users.updateUserByMsisdn("89528120000", new User("Username", "Usernamov"));

    Message expected = new Message(
        Map.of("user", "test",
            "msisdn", "89528120000",
            "first_name", "Username",
            "last_name", "Usernameov"),
        Message.EnrichmentType.MSISDN);
    Message actual = service.enrich(new Message(
        Map.of("user", "test"),
        Message.EnrichmentType.MSISDN));

    assertEquals(expected, actual);
  }

  @Test
  void enrichNotFound() {
    final EnrichmentService service = new EnrichmentService();

    Message message = new Message(Map.of("test", "test"), Message.EnrichmentType.MSISDN);

    Message expected = message;
    Message actual = service.enrich(message);

    assertEquals(expected, actual);
  }
}