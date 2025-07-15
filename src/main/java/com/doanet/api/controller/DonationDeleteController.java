package com.doanet.api.controller;

import com.doanet.api.entity.Donation;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.DeleteDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Donations", description = "Operações relacionadas às doações")
public class DonationDeleteController {
  private final DeleteDonationService deleteDonationService;

  public DonationDeleteController(DeleteDonationService deleteDonationService) {
    this.deleteDonationService = deleteDonationService;
  }

  @Operation(summary = "Delete de forma sistêmica a doação", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doação cancelada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping(value = "/delete/{id}", consumes = { "application/json" })
  public ResponseEntity<ApiSuccessResponse<Donation>> delete(@PathVariable("id") Long id){
    var donation = this.deleteDonationService.delete(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doação deletada com sucesso", donation);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
