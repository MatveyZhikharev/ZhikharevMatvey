package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Map;

public class EnrichmentServiceTest {
  @Test
  void enrich() {
    MapUserRepository users = new MapUserRepository();
    users.updateUserByMsisdn("89528120000", new User("Username", "Usernamov"));

    final EnrichmentService service = new EnrichmentService();
    service.addEnrichment(Message.EnrichmentType.MSISDN, new MsisdnEnrichment(users));


    Message expected = new Message(
        Map.of("user", "test",
            "msisdn", "89528120000",
            "firstName", "Username",
            "lastName", "Usernamov")
        ,
        Message.EnrichmentType.MSISDN);

    Message actual = service.enrich(new Message(
        Map.of("user", "test",
            "msisdn", "89528120000"),
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