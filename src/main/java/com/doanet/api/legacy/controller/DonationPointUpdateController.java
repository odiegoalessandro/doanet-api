package com.doanet.api.legacy.controller;

import com.doanet.api.legacy.dto.UpdateDonationPointDto;
import com.doanet.api.legacy.entity.DonationPoint;
import com.doanet.api.legacy.exception.ApiError;
import com.doanet.api.legacy.response.ApiSuccessResponse;
import com.doanet.api.legacy.service.UpdateDonationPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donation-point", produces = { "application/json" })
@Tag(name = "Donation Points", description = "Operações relacionadas aos pontos de doação")
public class DonationPointUpdateController {
  private final UpdateDonationPointService updateDonationPointService;

  public DonationPointUpdateController(UpdateDonationPointService updateDonationPointService) {
    this.updateDonationPointService = updateDonationPointService;
  }

  @Operation(summary = "Atualiza parcialmente os dados do ponto de doação pelo ID", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Ponto de doação atualizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "Ponto de doação não encontrado para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping(value = "/{id}", consumes = { "application/json" })
  public ResponseEntity<ApiSuccessResponse<DonationPoint>> update(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID do ponto de doação a ser atualizado")
    Long id,
    @Valid
    @RequestBody
    UpdateDonationPointDto updateDonationPointDto
  ){
    var donationPoint = this.updateDonationPointService.update(id, updateDonationPointDto);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Ponto de doação atualizado com sucesso",
      donationPoint
    );

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
