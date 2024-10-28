package org.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnrichmentService {
  private final ConcurrentHashMap<Message.EnrichmentType, Enrichment> enrichments = new ConcurrentHashMap<>();

  public interface Enrichment {
    Map<String, String> enrich(Map<String, String> input);
  }

  public void addEnrichment(Message.EnrichmentType type, Enrichment enrichment) {
    enrichments.put(type, enrichment);
  }

  public Message enrich(Message message) {
    Enrichment enrichment = enrichments.get(message.enrichmentType());
    if (enrichment == null) {
      return message;
    }

    Map<String, String> newContent = enrichment.enrich(new ConcurrentHashMap<>(message.content()));
    return new Message(newContent, message.enrichmentType());
  }
}
