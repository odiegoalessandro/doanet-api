package com.doanet.api.controller;

import com.doanet.api.dto.CreateItemDto;
import com.doanet.api.entity.Item;
import com.doanet.api.exception.ApiError;
import com.doanet.api.response.ApiSuccessResponse;
import com.doanet.api.service.CreateItemService;
import com.doanet.api.service.FindItemService;
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
@RequestMapping(value = "/item", produces = { "application/json" })
@Tag(name = "Items", description = "Operações relacionadas aos itens")
public class ItemController {

  private final CreateItemService createItemService;
  private final FindItemService findItemService;

  public ItemController(CreateItemService createItemService, FindItemService findItemService) {
    this.createItemService = createItemService;
    this.findItemService = findItemService;
  }

  @Operation(summary = "Realiza o cadastro do item", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiSuccessResponse<Item>> create(@RequestBody @Valid CreateItemDto itemDto) {
    Item result = this.createItemService.create(itemDto);
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "Item criado com sucesso", result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Busca item pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Item encontrado"),
    @ApiResponse(responseCode = "404", description = "Item não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Item>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID do item a ser buscado", example = "1")
    Long id
  ) {
    Item result = this.findItemService.findById(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Item encontrado com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Busca itens pelo nome (case insensitive) com paginação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Itens encontrados"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/search")
  public ResponseEntity<ApiSuccessResponse<Page<Item>>> findByName(
    @RequestParam
    @Parameter(name = "name", description = "Nome parcial do item para busca", example = "banana")
    String name,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página (0 baseado)", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade máxima de itens por página", example = "10")
    int pageSize
  ) {
    Page<Item> result = this.findItemService.findByName(name, pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Itens encontrados com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Busca todos os itens com paginação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Itens retornados"),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<Page<Item>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página (0 baseado)", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade máxima de itens por página", example = "10")
    int pageSize
  ) {
    Page<Item> result = this.findItemService.findAll(pageNumber, pageSize);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de itens retornada com sucesso", result);
    return ResponseEntity.ok(response);
  }
}
