package com.doanet.api.legacy.controller;

import com.doanet.api.legacy.dto.CreateDonationPointDto;
import com.doanet.api.legacy.entity.DonationPointEntity;
import com.doanet.api.legacy.exception.ApiError;
import com.doanet.api.legacy.response.ApiSuccessResponse;
import com.doanet.api.legacy.service.CreateDonationPointService;
import io.swagger.v3.oas.annotations.Operation;
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
public class DonationPointCreateController {

  private final CreateDonationPointService createDonationPointService;

  public DonationPointCreateController(CreateDonationPointService createDonationPointService) {
    this.createDonationPointService = createDonationPointService;
  }

  @PostMapping
  @Operation(summary = "Realiza o cadastro do ponto de doação", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Cadastro foi realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonationPointEntity>> create(
    @RequestBody @Valid CreateDonationPointDto donationPoint
  ) {
    DonationPointEntity result = this.createDonationPointService.create(
      donationPoint.user(),
      donationPoint.description()
    );
    var response = new ApiSuccessResponse<DonationPointEntity>(
      HttpStatus.CREATED,
      "Ponto de doação criado com sucesso",
      result
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


}
