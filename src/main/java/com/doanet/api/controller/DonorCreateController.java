package com.doanet.api.controller;

import com.doanet.api.dto.CreateDonorDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.CreateDonorService;
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
@RequestMapping(value = "/donor", produces = { "application/json" })
@Tag(name = "Donors", description = "Operações relacionadas aos doadores")
public class DonorCreateController {
  private final CreateDonorService createDonorService;

  public DonorCreateController(CreateDonorService createDonorService) {
    this.createDonorService = createDonorService;
  }

  @Operation(summary = "Realiza o cadastro do doador", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Cadastro foi realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiSuccessResponse<Donor>> create(@RequestBody @Valid CreateDonorDto donor){
    Donor result = this.createDonorService.create(donor.user(), donor.document(), donor.reasonSocial());
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "Doador criado com sucesso", result);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}

