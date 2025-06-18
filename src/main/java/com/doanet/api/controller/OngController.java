package com.doanet.api.controller;

import com.doanet.api.dto.CreateOngDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.CreateOngService;
import com.doanet.api.service.FindOngService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ong", produces = { "application/json" })
@Tag(name = "ONGs", description = "Operações relacionadas às ONGs")
public class OngController {
  private final CreateOngService createOngService;
  private final FindOngService findOngService;

  public OngController(CreateOngService createOngService, FindOngService findOngService){
    this.createOngService = createOngService;
    this.findOngService = findOngService;
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

  @Operation(summary = "Realiza a pesquisa da ONG pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e ONG foi encontrada"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas ONG não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Ong>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id da ONG a ser buscada")
    Long id
  ){
    Ong result = this.findOngService.findById(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "ONG encontrada com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/cnpj/{cnpj}")
  @Operation(summary = "Realiza a pesquisa da ONG pelo CNPJ", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e ONG foi encontrada"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas ONG não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Ong>> findByCnpj(
    @PathVariable("cnpj")
    @Parameter(name = "cnpj", description = "CNPJ da ONG a ser buscada", example = "Ong Turma do Bem")
    String cnpj
  ){
    Ong result = this.findOngService.findByCnpj(cnpj);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "ONG encontrada com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(summary = "Realiza a pesquisa da ONG`s pelo sistema e as retorna em paginas numeradas", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Page<Ong>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página a ser buscada", example = "1")
    Integer pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade maxima de itens retornados", example = "100")
    Integer pageSize
  ){
    Page<Ong> result = this.findOngService.findAll(pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de ONGs retornada com sucesso", result);
    return ResponseEntity.ok(response);
  }
}
