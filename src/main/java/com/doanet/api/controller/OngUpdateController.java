package com.doanet.api.controller;

import com.doanet.api.dto.UpdateOngDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.UpdateOngService;
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
@RequestMapping(value = "/ong", produces = { "application/json" })
@Tag(name = "ONGs", description = "Operações de atualização relacionadas às ONGs")
public class OngUpdateController {

  private final UpdateOngService updateOngService;

  public OngUpdateController(UpdateOngService updateOngService) {
    this.updateOngService = updateOngService;
  }

  @Operation(summary = "Atualiza parcialmente os dados da ONG pelo ID", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "ONG atualizada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "ONG não encontrada para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping(value = "/{id}", consumes = { "application/json" })
  public ResponseEntity<ApiSuccessResponse<Ong>> updatePartial(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da ONG a ser atualizada")
    Long id,
    @RequestBody
    @Valid
    UpdateOngDto updateOngDto
  ) {
    var ong = this.updateOngService.update(id, updateOngDto);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "ONG atualizada com sucesso", ong);
    return ResponseEntity.ok(response);
  }
}
