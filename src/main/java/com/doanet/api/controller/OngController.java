package com.doanet.api.controller;

import com.doanet.api.dto.CreateOngDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.service.CreateOngService;
import com.doanet.api.service.FindOngService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ong", produces = { "application/json" })
public class OngController {
  private final CreateOngService createOngService;
  private final FindOngService findOngService;

  public OngController(CreateOngService createOngService, FindOngService findOngService){
    this.createOngService = createOngService;
    this.findOngService = findOngService;
  }

  @Transactional
  @Operation(summary = "Realiza a criação de ONG`s dentro do sistema", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Criação de ONG realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Ong create(@RequestBody @Valid CreateOngDto ong){
    return this.createOngService.create(ong.user(), ong.cnpj());
  }
  @Operation(summary = "Realiza a pesquisa da ONG pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e ONG foi encontrada"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas ONG não foi encontrada"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/{id}")
  public Ong findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id da ONG a ser buscada")
    Long id
  ){
    return this.findOngService.findById(id);
  }

  @GetMapping("/cnpj/{cnpj}")
  @Operation(summary = "Realiza a pesquisa da ONG pelo CNPJ", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e ONG foi encontrada"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas ONG não foi encontrada"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public Ong findByCnpj(
    @PathVariable("cnpj")
    @Parameter(name = "cnpj", description = "CNPJ da ONG a ser buscada", example = "Ong Turma do Bem")
    String cnpj
  ){
    return this.findOngService.findByCnpj(cnpj);
  }

  @GetMapping
  @Operation(summary = "Realiza a pesquisa da ONG`s pelo sistema e as retorna em paginas numeradas", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public Page<Ong> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página a ser buscada")
    Integer pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade maxima de itens retornados")
    Integer pageSize
  ){
    return this.findOngService.findAll(pageNumber, pageSize);
  }
}
