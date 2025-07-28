package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.commands.UpdateOngCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.ong.*;
import com.doanet.api.domain.entities.ong.Ong;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ong", produces = { "application/json" })
@Tag(name = "ONGs", description = "Operações relacionadas às ONGs")
public class OngController {
  private final CreateOngUseCase createOngUseCase;
  private final FindActiveOngByIdUseCase findActiveOngByIdUseCase;
  private final FindAllActiveOngUseCase findAllActiveOngUseCase;
  private final FindActiveOngByCnpjUseCase findActiveOngByCnpjUseCase;
  private final UpdateOngUseCase updateOngUseCase;
  private final DeleteOngByIdUseCase deleteOngByIdUseCase;

  public OngController(CreateOngUseCase createOngUseCase,
                       FindActiveOngByIdUseCase findActiveOngByIdUseCase,
                       FindAllActiveOngUseCase findAllActiveOngUseCase,
                       FindActiveOngByCnpjUseCase findActiveOngByCnpjUseCase,
                       UpdateOngUseCase updateOngUseCase,
                       DeleteOngByIdUseCase deleteOngByIdUseCase) {
    this.createOngUseCase = createOngUseCase;
    this.findActiveOngByIdUseCase = findActiveOngByIdUseCase;
    this.findAllActiveOngUseCase = findAllActiveOngUseCase;
    this.findActiveOngByCnpjUseCase = findActiveOngByCnpjUseCase;
    this.updateOngUseCase = updateOngUseCase;
    this.deleteOngByIdUseCase = deleteOngByIdUseCase;
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

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<OngDto>> findById(
    @PathVariable("id") Long id
  ){
    Ong ong = this.findActiveOngByIdUseCase.execute(id);
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

  @GetMapping("/cnpj/{cnpj}")
  public ResponseEntity<ApiSuccessResponse<OngDto>> findByCnpj(
    @PathVariable("cnpj") String cnpj
  ){
    Ong ong = this.findActiveOngByCnpjUseCase.execute(cnpj);
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

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<PageResponse<OngDto>>> findAll(
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize
  ){
    PageResponse<Ong> page = this.findAllActiveOngUseCase.execute(pageNumber, pageSize);
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

  @Operation(summary = "Deleta a ONG", method = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Delete da ONG realizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Ong não foi encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<Void>> delete(
    @Parameter(name = "id", example = "1", description = "ID da ONG a ser deletada")
    @PathVariable("id") Long id
  ){
    this.deleteOngByIdUseCase.execute(id);
    var response = new ApiSuccessResponse<Void>(HttpStatus.OK, "Ong deletada com sucesso", null);

    return ResponseEntity.ok(response);
  }
}
