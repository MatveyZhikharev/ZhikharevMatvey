package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class EnrichmentServiceTest {
  @Test
  void enrich() {
    final EnrichmentService service = new EnrichmentService();
    service.addEnrichment(Message.EnrichmentType.MSISDN, new EnrichmentService.Enrichment() {
      @Override
      public Map<String, String> enrich(Map<String, String> content) {
        content.put("msisdn_enriched", "true");
        return content;
      }
    });

    Message expected = new Message(
        Map.of("test", "test", "msisdn_enriched", "true"),
        Message.EnrichmentType.MSISDN);
    Message actual = service.enrich(new Message(
        Map.of("test", "test"),
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