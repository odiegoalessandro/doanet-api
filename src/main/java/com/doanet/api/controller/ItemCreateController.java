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
public class ItemCreateController {

  private final CreateItemService createItemService;

  public ItemCreateController(CreateItemService createItemService) {
    this.createItemService = createItemService;
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
}
