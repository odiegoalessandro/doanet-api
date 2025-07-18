package com.doanet.api.legacy.controller;

import com.doanet.api.legacy.dto.UpdateDonationItemsDto;
import com.doanet.api.legacy.dto.UpdateDonationStatusDto;
import com.doanet.api.legacy.entity.Donation;
import com.doanet.api.legacy.exception.ApiError;
import com.doanet.api.legacy.response.ApiSuccessResponse;
import com.doanet.api.legacy.service.UpdateDonationService;
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
@RequestMapping(value = "/donation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Donations", description = "Operações relacionadas às doações")
public class DonationUpdateController {
  private UpdateDonationService updateDonationService;

  public DonationUpdateController(UpdateDonationService updateDonationService) {
    this.updateDonationService = updateDonationService;
  }

  @PatchMapping("/status/{id}")
  @Operation(description = "Atualiza os status da doação", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doação atualizada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Donation>> updateStatus(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da doação a ser atualizada")
    Long id,
    @RequestBody
    UpdateDonationStatusDto updateDonationStatusDto
  ){
    var donation = this.updateDonationService.updateStatus(id, updateDonationStatusDto.status());
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Doação teve seu status atualizado com sucesso",
      donation
    );

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }


  @Operation(description = "Atualiza os itens da doação", method = "PATCH")
  @PatchMapping("/{id}")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doação atualizada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Donation>> updateItems(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da doação a ser atualizada")
    Long id,
    @RequestBody
    UpdateDonationItemsDto updateDonationItemsDto
  ){
    var donation = this.updateDonationService.updateItems(id, updateDonationItemsDto);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Doação teve seus itens atualizados com sucesso",
      donation
    );

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
