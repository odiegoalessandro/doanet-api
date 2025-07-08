package com.doanet.api.controller;

import com.doanet.api.entity.Ong;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.DeleteOngService;
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
@RequestMapping(value = "/ong", produces = { "application/json" })
@Tag(name = "ONGs", description = "Operações relacionadas às ONGs")
public class OngDeleteController {
  private DeleteOngService deleteOngService;

  public OngDeleteController(DeleteOngService deleteOngService){
    this.deleteOngService = deleteOngService;
  }

  @Operation(summary = "Deleta a ONG", method = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Delete da ONG realizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Ong não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Ong>> delete(
    @Parameter(name = "id", example = "1", description = "ID da ONG a ser deletada")
    @PathVariable("id") Long id
  ){
    var ong = this.deleteOngService.delete(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Ong deletada com sucesso", ong);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
