package com.doanet.api.client;

import com.doanet.api.dto.CoordinatesDto;
import com.doanet.api.exception.CoordinatesInternalServerException;
import com.doanet.api.exception.CoordinatesNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeolocationClient {
  @Value("${opencage.api.key}")
  private String opencageApiKey;

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  public GeolocationClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
  }

  private CoordinatesDto extractCoordinates(String json) {
    try {
      JsonNode root = objectMapper.readTree(json);
      JsonNode results = root.path("results");

      if (!results.isArray() || results.isEmpty()) {
        throw new CoordinatesNotFoundException("Nenhuma coordenada encontrada para o endereço informado.");
      }

      JsonNode firstResult = results.get(0);

      validateResult(firstResult);

      JsonNode geometry = results.get(0).path("geometry");
      double latitude = geometry.get("lat").asDouble();
      double longitude = geometry.get("lng").asDouble();

      return new CoordinatesDto(latitude, longitude);
    } catch (Exception e) {
      throw new CoordinatesInternalServerException("Error ao extrair as coordenadas: " + e.getMessage());
    }
  }

  public CoordinatesDto setCoordinatesByAddress(String address){
    if (address == null || address.isBlank()) {
      throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio");
    }

    return getCoordinates(address);
  }

  private void validateResult(JsonNode firstResult) {
    int confidence = firstResult.path("confidence").asInt(0);
    String country = firstResult.path("components").path("country").asText();

    if (confidence < 5 || !"Brazil".equalsIgnoreCase(country)) {
      throw new CoordinatesNotFoundException("Endereço inválido ou fora do Brasil, pais: " + country );
    }
  }

  private CoordinatesDto getCoordinates(String address){
    try {
      var url = UriComponentsBuilder
        .fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
        .queryParam("q", address)
        .queryParam("key", opencageApiKey)
        .toUriString();

      var json = restTemplate.getForEntity(url, String.class).getBody();

      return this.extractCoordinates(json);
    } catch (Exception e) {
      throw new CoordinatesInternalServerException("Erro ao chamar API de geolocalização" + e.getMessage());
    }
  }
}
