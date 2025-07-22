package com.doanet.api.legacy.controller;

import com.doanet.api.legacy.entity.DonationPointEntity;
import com.doanet.api.legacy.exception.ApiError;
import com.doanet.api.legacy.service.DeleteDonationPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/donation-point", produces = { "application/json" })
@Tag(name = "Donation Points", description = "Operações relacionadas aos pontos de doação")
public class DonationPointDeleteController {
  private DeleteDonationPointService deleteDonationPointService;

  public DonationPointDeleteController(DeleteDonationPointService deleteDonationPointService) {
    this.deleteDonationPointService = deleteDonationPointService;
  }

  @Operation(description = "Deleta o ponto de doação", method = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Delete da ONG realizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Ong não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @DeleteMapping("/{id}")
  public DonationPointEntity delete(@PathVariable("id") Long donationPointId){
    return this.deleteDonationPointService.delete(donationPointId);
  }
}
