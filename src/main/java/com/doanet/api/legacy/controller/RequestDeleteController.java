package com.doanet.api.legacy.controller;

import com.doanet.api.legacy.entity.Request;
import com.doanet.api.legacy.exception.ApiError;
import com.doanet.api.legacy.response.ApiSuccessResponse;
import com.doanet.api.legacy.service.DeleteRequestService;
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
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Requests", description = "Operações relacionadas às solicitações")
public class RequestDeleteController {
  private final DeleteRequestService deleteRequestService;

  public RequestDeleteController(DeleteRequestService deleteRequestService) {
    this.deleteRequestService = deleteRequestService;
  }

  @Operation(summary = "Delete de forma sistêmica a solicitação", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitação cancelada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "Solicitação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping(value = "/delete/{id}", consumes = { "application/json" })
  public ResponseEntity<ApiSuccessResponse<Request>> delete(@PathVariable("id") Long id) {
    var request = this.deleteRequestService.delete(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Solicitação deletada com sucesso", request);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
