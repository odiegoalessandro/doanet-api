package com.doanet.api.config;

import com.doanet.api.application.gateways.GetCoordinatesByAddress;
import com.doanet.api.infra.gateways.OpenCageGetCoordinatesByAddressImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoordinatesConfig {

  @Value("${opencage.api.key}")
  private String apiKey;

  @Bean
  public GetCoordinatesByAddress getCoordinatesByAddress(RestTemplate restTemplate, ObjectMapper objectMapper) {
    return new OpenCageGetCoordinatesByAddressImpl(restTemplate, objectMapper, apiKey);
  }
}
