package com.doanet.api.controller;

import com.doanet.api.dto.CreateDonorDto;
import com.doanet.api.entity.Donor;
import com.doanet.api.service.CreateDonorService;
import com.doanet.api.service.FindDonorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donor", produces = { "application/json" })
public class DonorController {
  private final CreateDonorService createDonorService;
  private final FindDonorService findDonorService;

  public DonorController(CreateDonorService createDonorService, FindDonorService findDonorService){
    this.createDonorService = createDonorService;
    this.findDonorService = findDonorService;
  }

  @Operation(summary = "Realiza o cadastro do doador", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Cadastro foi realizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Donor create(@RequestBody CreateDonorDto donor){
    return this.createDonorService.create(donor.user(), donor.document(), donor.reasonSocial());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Realiza a pesquisa do doador pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e doador foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas doador não foi encontrado"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  public Donor findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id do doador a ser buscado", example = "1")
    Long id
  ){
    return this.findDonorService.findById(id);
  }

  @Operation(summary = "Realiza a pesquisa do doador pelo documento(CPF/CNPJ)", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e doador foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas doador não foi encontrado"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/document/{document}")
  public Donor findByDocument(
    @PathVariable("document")
    @Parameter(name = "document", description = "Documento do doardor a ser buscado", example = "10038372045")
    String document
  ){
    return this.findDonorService.findByDocument(document);
  }

  @Operation(summary = "Realiza a pesquisa do doador pela razão social", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e doador foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas doador não foi encontrado"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/reason-social/{reasonSocial}")
  public Donor findByReasonSocial(
    @PathVariable("reasonSocial")
    @Parameter(name = "reasonSocial", description = "Razão social do doador a ser buscado", example = "Diego A.C. Martins")
    String reasonSocial
  ){
    return this.findDonorService.findByReasonSocial(reasonSocial);
  }

  @Operation(summary = "Realiza a pesquisa dos doadores por páginas numeradas", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  public Page<Donor> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página a ser buscada", example = "1")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade maxima de itens a serem retornados", example = "100")
    int pageSize
  ){
    return this.findDonorService.findAll(pageNumber, pageSize);
  }
}
