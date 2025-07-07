package com.doanet.api.controller;


import com.doanet.api.entity.DonationPoint;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.FindDonationPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donation-point", produces = { "application/json" })
@Tag(name = "Donation Points", description = "Operações relacionadas aos pontos de doação")
public class DonationPointReadController {
  private FindDonationPointService findDonationPointService;

  public DonationPointReadController(FindDonationPointService findDonationPointService){
    this.findDonationPointService = findDonationPointService;
  }

  @Operation(summary = "Pesquisa ponto de doação pela descrição", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Ponto de doação não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/description/{description}")
  public ResponseEntity<ApiSuccessResponse<Page<DonationPoint>>> findByDescription(
    @PathVariable("description") String description,
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize
  ) {
    Page<DonationPoint> result = this.findDonationPointService.findByDescription(description, pageNumber, pageSize);
    var response = new ApiSuccessResponse<Page<DonationPoint>>(
      HttpStatus.OK,
      "Pontos de doação encontrados com sucesso",
      result
    );

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Busca ponto de doação por ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Ponto de doação encontrado"),
    @ApiResponse(responseCode = "404", description = "Ponto de doação não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<DonationPoint>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id do ponto de doação a ser buscado", example = "1")
    Long id
  ) {
    DonationPoint result = this.findDonationPointService.findById(id);
    var response = new ApiSuccessResponse<DonationPoint>(
      HttpStatus.OK,
      "Ponto de doação encontrado com sucesso",
      result
    );

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Lista todos os pontos de doação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<Page<DonationPoint>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página a ser buscada", example = "1")
    Integer pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade máxima de itens a serem retornados", example = "100")
    Integer pageSize
  ) {
    Page<DonationPoint> result = this.findDonationPointService.findAll(pageNumber, pageSize);
    ApiSuccessResponse<Page<DonationPoint>> response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de pontos de doação retornada com sucesso",
      result
    );
    return ResponseEntity.ok(response);
  }
}
