package com.doanet.api.controller;

import com.doanet.api.dto.CreateRequestDto;
import com.doanet.api.dto.ResponseRequestDto;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.CreateRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Requests", description = "Operações relacionadas às solicitações")
public class RequestCreateController {
  private final CreateRequestService createRequestService;

  public RequestCreateController(CreateRequestService createRequestService) {
    this.createRequestService = createRequestService;
  }

  @Operation(summary = "Realiza o cadastro da solicitação", method = "POST")
  @ApiResponse(responseCode = "201", description = "Solicitação criada com sucesso")
  @ApiResponse(responseCode = "400", description = "Dados inválidos",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiSuccessResponse<ResponseRequestDto>> create(
    @RequestBody @Valid CreateRequestDto createRequestDto
  ) {
    var result = createRequestService.create(createRequestDto);
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "Solicitação criada com sucesso", result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
