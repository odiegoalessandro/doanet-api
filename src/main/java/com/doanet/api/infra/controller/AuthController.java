package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.LoginCommand;
import com.doanet.api.application.commands.LogoutCommand;
import com.doanet.api.application.commands.RefreshTokenCommand;
import com.doanet.api.application.dto.AuthTokens;
import com.doanet.api.application.usecases.auth.LoginUseCase;
import com.doanet.api.application.usecases.auth.LogoutUseCase;
import com.doanet.api.application.usecases.auth.RefreshTokenUseCase;
import com.doanet.api.infra.interfaces.ApiError;
import com.doanet.api.infra.interfaces.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
@Tag(name = "Autenticação", description = "Operações de login, renovação e encerramento de sessão")
public class AuthController {

  private final LoginUseCase loginUseCase;
  private final RefreshTokenUseCase refreshTokenUseCase;
  private final LogoutUseCase logoutUseCase;

  public AuthController(LoginUseCase loginUseCase,
                        RefreshTokenUseCase refreshTokenUseCase,
                        LogoutUseCase logoutUseCase) {
    this.loginUseCase = loginUseCase;
    this.refreshTokenUseCase = refreshTokenUseCase;
    this.logoutUseCase = logoutUseCase;
  }

  @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(summary = "Autentica o usuário e emite os tokens de acesso e de renovação")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<AuthTokens>> login(@RequestBody @Valid LoginCommand command) {
    var tokens = this.loginUseCase.execute(command);
    return ResponseEntity.ok(new ApiSuccessResponse<>(HttpStatus.OK, "Login realizado com sucesso", tokens));
  }

  @PostMapping(value = "/refresh", consumes = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(summary = "Rotaciona o refresh token e emite um novo access token")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Tokens renovados com sucesso"),
    @ApiResponse(responseCode = "401", description = "Refresh token inválido, expirado ou reutilizado",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<AuthTokens>> refresh(@RequestBody @Valid RefreshTokenCommand command) {
    var tokens = this.refreshTokenUseCase.execute(command);
    return ResponseEntity.ok(new ApiSuccessResponse<>(HttpStatus.OK, "Tokens renovados com sucesso", tokens));
  }

  @PostMapping(value = "/logout", consumes = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(summary = "Revoga o refresh token informado")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso")
  })
  public ResponseEntity<ApiSuccessResponse<Void>> logout(@RequestBody @Valid LogoutCommand command) {
    this.logoutUseCase.execute(command);
    return ResponseEntity.ok(new ApiSuccessResponse<>(HttpStatus.OK, "Logout realizado com sucesso", null));
  }
}
