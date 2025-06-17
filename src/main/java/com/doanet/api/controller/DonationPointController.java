package com.doanet.api.controller;

import com.doanet.api.dto.CreateDonationPointDto;
import com.doanet.api.entity.DonationPoint;
import com.doanet.api.service.CreateDonationPointService;
import com.doanet.api.service.FindDonationPointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donation-point", produces = { "application/json" })
public class DonationPointController {
  private final CreateDonationPointService createDonationPointService;
  private final FindDonationPointService findDonationPointService;

  public DonationPointController(
      CreateDonationPointService createDonationPointService,
      FindDonationPointService findDonationPointService
  ) {
    this.createDonationPointService = createDonationPointService;
    this.findDonationPointService = findDonationPointService;
  }

  @PostMapping
  @Operation(summary = "Realiza o cadastro do ponto de doação", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Cadastro foi realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @Transactional
  public DonationPoint create(@RequestBody @Valid CreateDonationPointDto donationPoint) {
    return this.createDonationPointService.create(donationPoint.user(), donationPoint.description());
  }

  @Operation(summary = "Realiza a pesquisa do ponto de doação pela descrição", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e ponto foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas o ponto não foi encontrado"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/description/{description}")
  public Page<DonationPoint> findByDescription(
      @PathVariable("description") String description,
      @RequestParam Integer pageNumber,
      @RequestParam Integer pageSize
  ){
    return this.findDonationPointService.findByDescription(description, pageNumber, pageSize);
  }

  @Operation(summary = "Realiza a pesquisa do ponto de doação pela descrição", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e ponto foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas o ponto não foi encontrado"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/{id}")
  public DonationPoint findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id do ponto de doação a ser buscado", example = "1")
    Long id
  ){
    return this.findDonationPointService.findById(id);
  }

  @Operation(summary = "Realiza a pesquisa do ponto de doação pela descrição", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  public Page<DonationPoint> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página a ser buscada", example = "1")
    Integer pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade maxima de itens a serem retornados", example = "100")
    Integer pageSize
  ){
    return this.findDonationPointService.findAll(pageNumber, pageSize);
  }
}


