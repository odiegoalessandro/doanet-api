package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.commands.UpdateOngCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.ong.*;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.infra.interfaces.ApiError;
import com.doanet.api.infra.interfaces.ApiSuccessResponse;
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
// TODO: Modificar as pesquisas por ID e findAll para retornar pontos ativos e inativos conforme o usuário passe via query param
@RestController
@RequestMapping(value = "/ong", produces = { "application/json" })
@Tag(name = "ONGs", description = "Operações relacionadas às ONGs")
public class OngController {
  private final CreateOngUseCase createOngUseCase;
  private final FindOngByIdUseCase findOngByIdUseCase;
  private final FindOngUseCase findOngUseCase;
  private final FindOngByCnpjUseCase findOngByCnpjUseCase;
  private final UpdateOngUseCase updateOngUseCase;
  private final DisableOngByIdUseCase disableOngByIdUseCase;

  public OngController(CreateOngUseCase createOngUseCase,
                       FindOngByIdUseCase findOngByIdUseCase,
                       FindOngUseCase findOngUseCase,
                       FindOngByCnpjUseCase findOngByCnpjUseCase,
                       UpdateOngUseCase updateOngUseCase,
                       DisableOngByIdUseCase disableOngByIdUseCase) {
    this.createOngUseCase = createOngUseCase;
    this.findOngByIdUseCase = findOngByIdUseCase;
    this.findOngUseCase = findOngUseCase;
    this.findOngByCnpjUseCase = findOngByCnpjUseCase;
    this.updateOngUseCase = updateOngUseCase;
    this.disableOngByIdUseCase = disableOngByIdUseCase;
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
  public ResponseEntity<ApiSuccessResponse<OngDto>> create(@RequestBody @Valid CreateOngCommand ong){
    var ongEntity = this.createOngUseCase.execute(ong);
    var user = ongEntity.getUser();
    var userDto = new UserDto(
      user.getId(),
      user.getEmail(),
      user.getPhone(),
      user.getStreet(),
      user.getNumber(),
      user.getNeighborhood(),
      user.getName(),
      user.getCity(),
      user.getState(),
      user.getZipCode()
    );
    var result = new OngDto(ongEntity.getId(), ongEntity.getCnpj(), userDto);
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "ONG criada com sucesso", result);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
  @Operation(summary = "Busca ONG por ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "ONG encontrada com sucesso",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "404", description = "ONG não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<OngDto>> findById(
    @PathVariable("id") Long id,
    @RequestParam boolean isActive
  ){
    Ong ong = this.findOngByIdUseCase.execute(id, isActive);
    var user = ong.getUser();
    var userDto = new UserDto(
      user.getId(),
      user.getEmail(),
      user.getPhone(),
      user.getStreet(),
      user.getNumber(),
      user.getNeighborhood(),
      user.getName(),
      user.getCity(),
      user.getState(),
      user.getZipCode()
    );
    var result = new OngDto(ong.getId(), ong.getCnpj(), userDto);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "ONG encontrada com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Busca ONG por CNPJ")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "ONG encontrada com sucesso",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "404", description = "ONG não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/cnpj/{cnpj}")
  public ResponseEntity<ApiSuccessResponse<OngDto>> findByCnpj(
    @PathVariable("cnpj") String cnpj,
    @RequestParam boolean isActive
  ){
    Ong ong = this.findOngByCnpjUseCase.execute(cnpj, isActive);
    var user = ong.getUser();
    var userDto = new UserDto(
      user.getId(),
      user.getEmail(),
      user.getPhone(),
      user.getStreet(),
      user.getNumber(),
      user.getNeighborhood(),
      user.getName(),
      user.getCity(),
      user.getState(),
      user.getZipCode()
    );
    var result = new OngDto(ong.getId(), ong.getCnpj(), userDto);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "ONG encontrada com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Lista todas as ONGs")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de ONGs retornada com sucesso",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<PageResponse<OngDto>>> findAll(
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize,
    @RequestParam boolean isActive
  ){
    PageResponse<Ong> page = this.findOngUseCase.execute(pageNumber, pageSize, isActive);
    var dtoList = page.content().stream().map(ong -> {
      var user = ong.getUser();
      var userDto = new UserDto(
        user.getId(),
        user.getEmail(),
        user.getPhone(),
        user.getStreet(),
        user.getNumber(),
        user.getNeighborhood(),
        user.getName(),
        user.getCity(),
        user.getState(),
        user.getZipCode()
      );
      return new OngDto(ong.getId(), ong.getCnpj(), userDto);
    }).toList();
    var pageDto = new PageResponse<>(dtoList, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de ONGs retornada com sucesso", pageDto);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Atualiza parcialmente os dados da ONG pelo ID", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "ONG atualizada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "ONG não encontrada para atualização",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping(value = "/{id}", consumes = { "application/json" })
  public ResponseEntity<ApiSuccessResponse<OngDto>> updatePartial(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da ONG a ser atualizada")
    Long id,
    @RequestBody
    @Valid
    UpdateOngCommand updateOngDto
  ) {
    var ong = this.updateOngUseCase.execute(id, updateOngDto);
    var user = ong.getUser();
    var userDto = new UserDto(
      user.getId(),
      user.getEmail(),
      user.getPhone(),
      user.getStreet(),
      user.getNumber(),
      user.getNeighborhood(),
      user.getName(),
      user.getCity(),
      user.getState(),
      user.getZipCode()
    );
    var result = new OngDto(ong.getId(), ong.getCnpj(), userDto);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "ONG atualizada com sucesso", result);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Desativa a ONG", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Desativa da ONG realizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Ong não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PatchMapping("/disable/{id}")
  public ResponseEntity<ApiSuccessResponse<Void>> disable(
    @Parameter(name = "id", example = "1", description = "ID da ONG a ser desativada")
    @PathVariable("id") Long id
  ){
    this.disableOngByIdUseCase.execute(id);
    var response = new ApiSuccessResponse<Void>(HttpStatus.OK, "Ong desativada com sucesso", null);

    return ResponseEntity.ok(response);
  }
}
