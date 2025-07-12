package com.doanet.api.controller;

import com.doanet.api.dto.UpdateRequestItemsDto;
import com.doanet.api.dto.UpdateRequestStatusDto;
import com.doanet.api.entity.Request;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.UpdateRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class RequestUpdateController {

  private final UpdateRequestService updateRequestService;

  public RequestUpdateController(UpdateRequestService updateRequestService) {
    this.updateRequestService = updateRequestService;
  }

  @PatchMapping("/status/{id}")
  @Operation(description = "Atualiza o status da solicitação", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitação atualizada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Solicitação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Request>> updateStatus(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da solicitação a ser atualizada")
    Long id,
    @RequestBody UpdateRequestStatusDto updateRequestStatusDto
  ) {
    var request = this.updateRequestService.updateStatus(id, updateRequestStatusDto.status());
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Solicitação teve seu status atualizado com sucesso",
      request
    );
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/{id}")
  @Operation(description = "Atualiza os itens da solicitação", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Solicitação atualizada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Solicitação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Request>> updateItems(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da solicitação a ser atualizada")
    Long id,
    @RequestBody UpdateRequestItemsDto updateRequestItemsDto
  ) {
    var request = this.updateRequestService.updateItems(id, updateRequestItemsDto);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Solicitação teve seus itens atualizados com sucesso",
      request
    );
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
