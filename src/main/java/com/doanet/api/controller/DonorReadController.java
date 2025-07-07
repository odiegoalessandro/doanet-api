package com.doanet.api.controller;

import com.doanet.api.entity.Donor;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.FindDonorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/donor", produces = { "application/json" })
@Tag(name = "Donors", description = "Operações relacionadas aos doadores")
public class DonorReadController {
  private final FindDonorService findDonorService;

  public DonorReadController(FindDonorService findDonorService) {
    this.findDonorService = findDonorService;
  }

  @GetMapping("/{id}")
  @Operation(summary = "Realiza a pesquisa do doador pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e doador foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas doador não foi encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Donor>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id do doador a ser buscado", example = "1")
    Long id
  ){
    Donor result = this.findDonorService.findById(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador encontrado com sucesso", result);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Realiza a pesquisa do doador pelo documento(CPF/CNPJ)", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e doador foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas doador não foi encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/document/{document}")
  public ResponseEntity<ApiSuccessResponse<Donor>> findByDocument(
    @PathVariable("document")
    @Parameter(name = "document", description = "Documento do doardor a ser buscado", example = "10038372045")
    String document
  ){
    Donor result = this.findDonorService.findByDocument(document);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador encontrado com sucesso", result);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Realiza a pesquisa do doador pela razão social", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada e doador foi encontrado"),
    @ApiResponse(responseCode = "404", description = "Pesquisa realizada mas doador não foi encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/reason-social/{reasonSocial}")
  public ResponseEntity<ApiSuccessResponse<Donor>> findByReasonSocial(
    @PathVariable("reasonSocial")
    @Parameter(
      name = "reasonSocial",
      description = "Razão social do doador a ser buscado",
      example = "Diego A.C. Martins"
    )
    String reasonSocial
  ){
    Donor result = this.findDonorService.findByReasonSocial(reasonSocial);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador encontrado com sucesso", result);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Realiza a pesquisa dos doadores por páginas numeradas", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<Page<Donor>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página a ser buscada", example = "1")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade maxima de itens a serem retornados", example = "100")
    int pageSize
  ){
    Page<Donor> result = this.findDonorService.findAll(pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de doadores retornada com sucesso", result);

    return ResponseEntity.ok(response);
  }
}
