package com.doanet.api.integration;

import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.gateways.UserRepository;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.UserType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthFlowIntegrationTest {

  private static final String RAW_PASSWORD = "senha123";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordHasher passwordHasher;

  private String email;

  @BeforeEach
  void setUp() {
    email = "user-" + UUID.randomUUID() + "@email.com";

    var user = new User(
      null,
      "João",
      email,
      passwordHasher.hash(RAW_PASSWORD),
      "11999999999",
      "Rua A",
      "123",
      "Centro",
      "São Paulo",
      "SP",
      "12345-678",
      null,
      null,
      UserType.DONOR,
      true
    );

    userRepository.save(user);
  }

  private JsonNode login(String password) throws Exception {
    var body = objectMapper.writeValueAsString(Map.of("email", email, "password", password));

    var response = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andReturn();

    return objectMapper.readTree(response.getResponse().getContentAsString());
  }

  private JsonNode loginSuccessfully() throws Exception {
    var body = objectMapper.writeValueAsString(Map.of("email", email, "password", RAW_PASSWORD));

    var response = mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
      .andReturn();

    return objectMapper.readTree(response.getResponse().getContentAsString()).path("data");
  }

  @Test
  void shouldReturnAccessAndRefreshTokens_OnValidLogin() throws Exception {
    var data = loginSuccessfully();

    assertFalse(data.path("accessToken").asText().isBlank());
    assertFalse(data.path("refreshToken").asText().isBlank());
    assertEquals(900L, data.path("expiresIn").asLong());
    assertNotEquals(data.path("accessToken").asText(), data.path("refreshToken").asText());
  }

  @Test
  void shouldReturnUnauthorized_OnInvalidPassword() throws Exception {
    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("email", email, "password", "senha-errada"))))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.status").value(401));
  }

  @Test
  void shouldReturnUnauthorized_ForUnknownEmail() throws Exception {
    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("email", "ninguem@email.com", "password", RAW_PASSWORD))))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.message").value("Email ou senha inválidos"));
  }

  @Test
  void shouldRejectProtectedRoute_WithoutToken() throws Exception {
    mockMvc.perform(get("/donor")
        .param("pageNumber", "0")
        .param("pageSize", "10")
        .param("isActive", "true"))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.status").value(401));
  }

  @Test
  void shouldAllowProtectedRoute_WithValidAccessToken() throws Exception {
    var accessToken = loginSuccessfully().path("accessToken").asText();

    mockMvc.perform(get("/donor")
        .header("Authorization", "Bearer " + accessToken)
        .param("pageNumber", "0")
        .param("pageSize", "10")
        .param("isActive", "true"))
      .andExpect(status().isOk());
  }

  @Test
  void shouldRotateTokens_OnRefresh() throws Exception {
    var firstRefreshToken = loginSuccessfully().path("refreshToken").asText();

    var response = mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", firstRefreshToken))))
      .andExpect(status().isOk())
      .andReturn();

    var rotated = objectMapper.readTree(response.getResponse().getContentAsString()).path("data");

    assertFalse(rotated.path("accessToken").asText().isBlank());
    assertNotEquals(firstRefreshToken, rotated.path("refreshToken").asText());
  }

  @Test
  void shouldRejectRefresh_WhenTokenIsUnknown() throws Exception {
    mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", "token-inexistente"))))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.message").value("Refresh token inválido"));
  }

  @Test
  void shouldRevokeEverySession_WhenRefreshTokenIsReused() throws Exception {
    var firstRefreshToken = loginSuccessfully().path("refreshToken").asText();

    var rotatedBody = mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", firstRefreshToken))))
      .andExpect(status().isOk())
      .andReturn();
    var secondRefreshToken = objectMapper.readTree(rotatedBody.getResponse().getContentAsString())
      .path("data").path("refreshToken").asText();

    mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", firstRefreshToken))))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.message").value("Refresh token reutilizado, faça login novamente"));

    mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", secondRefreshToken))))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void shouldInvalidateRefreshToken_OnLogout() throws Exception {
    var refreshToken = loginSuccessfully().path("refreshToken").asText();

    mockMvc.perform(post("/auth/logout")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", refreshToken))))
      .andExpect(status().isOk());

    mockMvc.perform(post("/auth/refresh")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("refreshToken", refreshToken))))
      .andExpect(status().isUnauthorized());
  }

  @Test
  void shouldHashPasswordOnLogin_WhenStoredPasswordIsLegacyPlaintext() throws Exception {
    var legacyEmail = "legacy-" + UUID.randomUUID() + "@email.com";
    userRepository.save(new User(
      null, "Maria", legacyEmail, RAW_PASSWORD, "11999999999",
      "Rua A", "123", "Centro", "São Paulo", "SP", "12345-678",
      null, null, UserType.DONOR, true
    ));

    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(Map.of("email", legacyEmail, "password", RAW_PASSWORD))))
      .andExpect(status().isOk());

    var stored = userRepository.findByEmail(legacyEmail).orElseThrow();
    assertNotEquals(RAW_PASSWORD, stored.getPassword());
    assertTrue(passwordHasher.isHashed(stored.getPassword()));
    assertTrue(passwordHasher.matches(RAW_PASSWORD, stored.getPassword()));
  }
}
