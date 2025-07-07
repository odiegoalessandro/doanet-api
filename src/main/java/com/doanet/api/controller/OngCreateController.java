package com.doanet.api.controller;

import com.doanet.api.dto.CreateOngDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.CreateOngService;
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
@RequestMapping(value = "/ong", produces = { "application/json" })
@Tag(name = "ONGs", description = "Operações relacionadas às ONGs")
public class OngCreateController {
  private CreateOngService createOngService;

  public OngCreateController(CreateOngService createOngService){
    this.createOngService = createOngService;
  }

  @Operation(summary = "Realiza a criação de ONG`s dentro do sistema", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Criação de ONG realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiSuccessResponse<Ong>> create(@RequestBody @Valid CreateOngDto ong){
    Ong result = this.createOngService.create(ong.user(), ong.cnpj());
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "ONG criada com sucesso", result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

}
