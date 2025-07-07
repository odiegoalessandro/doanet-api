package com.doanet.api.controller;

import com.doanet.api.dto.UpdateDonorDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.UpdateDonorService;
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
@RequestMapping(value = "/donor", produces = { "application/json" })
@Tag(name = "Donors", description = "Operações de atualização relacionadas aos doadores")
public class DonorUpdateController {

  private final UpdateDonorService updateDonorService;

  public DonorUpdateController(UpdateDonorService updateDonorService) {
    this.updateDonorService = updateDonorService;
  }

  @Operation(summary = "Atualiza parcialmente os dados do doador pelo ID", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador atualizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping(value = "/{id}", consumes = { "application/json" })
  public ResponseEntity<ApiSuccessResponse<Donor>> update(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID do doador a ser atualizado")
    Long id,
    @RequestBody
    @Valid
    UpdateDonorDto updateDonorDto
  ) {
    var donor = this.updateDonorService.update(id, updateDonorDto);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador atualizado com sucesso", donor);
    return ResponseEntity.ok(response);
  }
}
