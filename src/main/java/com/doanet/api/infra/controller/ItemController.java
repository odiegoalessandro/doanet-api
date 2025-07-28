package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateItemCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.item.CreateItemUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByNameUseCase;
import com.doanet.api.domain.entities.item.Item;
import com.doanet.api.infra.persistence.ItemEntity;
import com.doanet.api.interfaces.ApiError;
import com.doanet.api.interfaces.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/item", produces = { "application/json" })
@Tag(name = "Itens", description = "Operações relacionadas aos Itens")
public class ItemController {

  private final CreateItemUseCase createItemUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;
  private final FindItemByNameUseCase findItemByNameUseCase;

  public ItemController(CreateItemUseCase createItemUseCase,
                        FindItemByIdUseCase findItemByIdUseCase,
                        FindItemByNameUseCase findItemByNameUseCase) {
    this.createItemUseCase = createItemUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
    this.findItemByNameUseCase = findItemByNameUseCase;
  }

  private ItemDto mapToDto(Item item) {
    return new ItemDto(
      item.getId(),
      item.getName(),
      item.getDescription(),
      item.isPerishable(),
      item.getExpirationDate()
    );
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Cadastra um novo item", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<ItemDto>> create(@RequestBody @Valid CreateItemCommand itemDto) {
    var result = createItemUseCase.execute(itemDto);
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "Item criado com sucesso", mapToDto(result));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Busca item por ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Item encontrado"),
    @ApiResponse(responseCode = "404", description = "Item não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<ItemDto>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID do item a ser buscado", example = "1") Long id
  ) {
    var result = findItemByIdUseCase.execute(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Item encontrado com sucesso", mapToDto(result));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/search")
  @Operation(summary = "Busca itens por nome (case insensitive) com paginação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Itens encontrados"),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<ItemDto>>> findByName(
    @RequestParam
    @Parameter(name = "name", description = "Nome parcial do item para busca", example = "banana") String name,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página (0 baseado)", example = "0") int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade máxima de itens por página", example = "10") int pageSize
  ) {
    var result = findItemByNameUseCase.execute(name, pageNumber, pageSize);
    var mapped = result.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(mapped, result.totalElements(), result.totalPages(), result.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Itens encontrados com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/name/{name}")
  @Operation(summary = "Busca todos os itens com paginação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Itens retornados"),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<ItemDto>>> findItemByName(
    @PathVariable("name") String name,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página (0 baseado)", example = "0") int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Quantidade máxima de itens por página", example = "10") int pageSize
  ) {
    var result = findItemByNameUseCase.execute(name, pageNumber, pageSize);
    var mapped = result.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(mapped, result.totalElements(), result.totalPages(), result.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de itens retornada com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }
}
