package com.doanet.api.controller;

import com.doanet.api.dto.DetailedResponseRequestDto;
import com.doanet.api.dto.ResponseRequestDto;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.FindRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Requests", description = "Operações relacionadas às solicitações")
public class RequestReadController {
  private FindRequestService findRequestService;

  public RequestReadController(FindRequestService findRequestService){
    this.findRequestService = findRequestService;
  }

  @Operation(summary = "Pesquisa solicitação pelo ID", method = "GET")
  @ApiResponse(responseCode = "200", description = "Solicitação encontrada")
  @ApiResponse(responseCode = "404", description = "Solicitação não encontrada",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<DetailedResponseRequestDto>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id da solicitação a ser buscada", example = "1")
    Long id
  ) {
    var result = findRequestService.findDtoById(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Solicitação encontrada com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa todas as solicitações paginadas", method = "GET")
  @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada")
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<Page<ResponseRequestDto>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0") int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50") int pageSize
  ) {
    var result = findRequestService.findAllDto(pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de solicitações retornada com sucesso",
      result
    );
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa solicitações por ID da ONG", method = "GET")
  @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada")
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/ong/{ongId}")
  public ResponseEntity<ApiSuccessResponse<Page<ResponseRequestDto>>> findByOngId(
    @PathVariable("ongId")
    @Parameter(name = "ongId", description = "Id da ONG", example = "1") Long ongId,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0") int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50") int pageSize
  ) {
    var result = findRequestService.findByOngIdDto(ongId, pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de solicitações da ONG retornada com sucesso",
      result
    );
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa solicitações por ID do ponto de doação", method = "GET")
  @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada")
  @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
    content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/donation-point/{donationPointId}")
  public ResponseEntity<ApiSuccessResponse<Page<ResponseRequestDto>>> findByDonationPointId(
    @PathVariable("donationPointId")
    @Parameter(name = "donationPointId", description = "Id do ponto de doação", example = "1") Long donationPointId,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0") int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50") int pageSize
  ) {
    var result = findRequestService.findByDonationPointIdDto(
      donationPointId,
      pageNumber,
      pageSize
    );
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de solicitações do ponto de doação retornada com sucesso",
      result
    );
    return ResponseEntity.ok(response);
  }
}
