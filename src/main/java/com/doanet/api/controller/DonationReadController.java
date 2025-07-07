package com.doanet.api.controller;

import com.doanet.api.dto.DetailedResponseDonationDto;
import com.doanet.api.dto.ResponseDonationDto;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.FindDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Donations", description = "Operações relacionadas às doações")
public class DonationReadController {

  private FindDonationService findDonationService;

  public DonationReadController(FindDonationService findDonationService){
    this.findDonationService = findDonationService;
  }

  @Operation(summary = "Pesquisa doação pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doação encontrada"),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<DetailedResponseDonationDto>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id da doação a ser buscada", example = "1")
    Long id
  ) {
    var result = findDonationService.findDtoById(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doação encontrada com sucesso", result);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa todas as doações paginadas", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de doações retornada"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<Page<ResponseDonationDto>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var result = findDonationService.findAllDto(pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de doações retornada com sucesso", result);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa doações pelo ID do doador", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de doações retornada"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/donor/{donorId}")
  public ResponseEntity<ApiSuccessResponse<Page<ResponseDonationDto>>> findByDonorId(
    @PathVariable("donorId")
    @Parameter(name = "donorId", description = "Id do doador", example = "1")
    Long donorId,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var result = findDonationService.findByDonorIdDto(donorId, pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de doações por doador retornada com sucesso",
      result
    );

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa doações pelo ID do ponto de doação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de doações retornada"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/donation-point/{donationPointId}")
  public ResponseEntity<ApiSuccessResponse<Page<ResponseDonationDto>>> findByDonationPointId(
    @PathVariable("donationPointId")
    @Parameter(name = "donationPointId", description = "Id do ponto de doação", example = "1")
    Long donationPointId,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var result = findDonationService.findByDonationPointIdDto(
      donationPointId,
      pageNumber,
      pageSize
    );
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de doações por ponto de doação retornada com sucesso",
      result
    );
    return ResponseEntity.ok(response);
  }
}
