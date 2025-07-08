package com.doanet.api.controller;

import com.doanet.api.entity.Donor;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.DeleteDonorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/donor", produces = { "application/json" })
@Tag(name = "Donors", description = "Operações relacionadas aos doadores")
public class DonorDeleteController {
  private DeleteDonorService deleteDonorService;

  public DonorDeleteController(DeleteDonorService deleteDonorService){
    this.deleteDonorService = deleteDonorService;
  }

  @Operation(summary = "Deleta o doador", method = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Delete do doador realizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Doador não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Donor>> delete(
    @Parameter(name = "id", example = "1", description = "ID do Doador a ser deletado")
    @PathVariable("id")
    Long id
  ){
    var ong = this.deleteDonorService.delete(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador deletado com sucesso", ong);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
