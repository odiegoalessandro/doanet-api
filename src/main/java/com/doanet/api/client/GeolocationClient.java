package com.doanet.api.client;

import com.doanet.api.dto.CoordinetesDto;
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
  private final ObjectMapper objectMapper = new ObjectMapper();

  public GeolocationClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  private CoordinetesDto extractCoordinates(String json) {
    try {
      JsonNode root = objectMapper.readTree(json);
      JsonNode geometry = root.path("results").get(0).path("geometry");

      double latitude = geometry.get("lat").asDouble();
      double longitude = geometry.get("lng").asDouble();

      return new CoordinetesDto(latitude, longitude);
    } catch (Exception e) {
      throw new RuntimeException("Error to extract coordinates ", e);
    }
  }

  public CoordinetesDto getCoordinetes(String address){
    var url = UriComponentsBuilder
        .fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
        .queryParam("q", address)
        .queryParam("key", opencageApiKey)
        .toUriString();

    var json = restTemplate.getForEntity(url, String.class).getBody();

    return this.extractCoordinates(json);
  }
}
