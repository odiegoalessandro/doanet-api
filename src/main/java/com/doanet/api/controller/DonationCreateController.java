package com.doanet.api.controller;

import com.doanet.api.dto.CreateDonationDto;
import com.doanet.api.dto.ResponseDonationDto;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.CreateDonationService;
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
@RequestMapping(value = "/donation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Donations", description = "Operações relacionadas às doações")
public class DonationCreateController {
  private CreateDonationService createDonationService;

  public DonationCreateController(CreateDonationService createDonationService){
    this.createDonationService = createDonationService;
  }

  @Operation(summary = "Realiza o cadastro da doação", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Doação criada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiSuccessResponse<ResponseDonationDto>> create(
    @RequestBody
    @Valid
    CreateDonationDto createDonationDto
  ) {
    var result = createDonationService.create(createDonationDto);
    var response = new ApiSuccessResponse<ResponseDonationDto>(
      HttpStatus.CREATED,
      "Doação criada com sucesso",
      result
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
