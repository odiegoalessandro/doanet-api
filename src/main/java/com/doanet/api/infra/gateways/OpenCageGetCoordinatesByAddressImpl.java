package com.doanet.api.infra.gateways;

import com.doanet.api.application.dto.Coordinates;
import com.doanet.api.application.exceptions.CoordinatesInternalServerException;
import com.doanet.api.application.exceptions.CoordinatesNotFoundException;
import com.doanet.api.application.gateways.GetCoordinatesByAddress;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenCageGetCoordinatesByAddressImpl implements GetCoordinatesByAddress {

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private final String apiKey;

  public OpenCageGetCoordinatesByAddressImpl(
    RestTemplate restTemplate,
    ObjectMapper objectMapper,
    @Value("${opencage.api.key}") String apiKey
  ) {
    this.restTemplate = restTemplate;
    this.objectMapper = objectMapper;
    this.apiKey = apiKey;
  }

  @Override
  public Coordinates execute(String address) {
    if (address == null || address.isBlank()) {
      throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio");
    }

    try {
      String url = UriComponentsBuilder
        .fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
        .queryParam("q", address)
        .queryParam("key", apiKey)
        .toUriString();

      String json = restTemplate.getForEntity(url, String.class).getBody();

      return parseCoordinates(json);
    } catch (Exception e) {
      throw new CoordinatesInternalServerException("Erro ao chamar API de geolocalização", e);
    }
  }

  private Coordinates parseCoordinates(String json) {
    try {
      JsonNode results = objectMapper.readTree(json).path("results");

      if (!results.isArray() || results.isEmpty()) {
        throw new CoordinatesNotFoundException("Nenhuma coordenada encontrada para o endereço.");
      }

      JsonNode geometry = results.get(0).path("geometry");
      double lat = geometry.path("lat").asDouble();
      double lng = geometry.path("lng").asDouble();

      return new Coordinates(lat, lng);
    } catch (Exception e) {
      throw new CoordinatesInternalServerException("Erro ao extrair coordenadas da resposta", e);
    }
  }
}
