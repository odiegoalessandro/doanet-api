package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateDonationPointCommand;
import com.doanet.api.application.commands.UpdateDonationPointCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.donationpoint.*;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.infra.interfaces.ApiError;
import com.doanet.api.infra.interfaces.ApiSuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
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

// TODO: Criar rota de delete para que ela seja feita o delete sistemico(UPDATE users SET is_active = false WHERE id
//  = ?) para isso deve ser feito useCase de User e implementando nas controller de donor, donationPoint e ong
@RestController
@RequestMapping(value = "/donation-point", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pontos de Doação", description = "Operações relacionadas aos Pontos de Doação")
public class DonationPointController {

  private final CreateDonationPointUseCase createDonationPointUseCase;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;
  private final FindDonationPointByDescriptionUseCase findDonationPointByDescriptionUseCase;
  private final FindAllDonationPointsUseCase findAllDonationPointsUseCase;
  private final UpdateDonationPointUseCase updateDonationPointUseCase;
  private final DeleteDonationPointUseCase deleteDonationPointUseCase;

  public DonationPointController(
    CreateDonationPointUseCase createDonationPointUseCase,
    FindDonationPointByIdUseCase findDonationPointByIdUseCase,
    FindDonationPointByDescriptionUseCase findDonationPointByDescriptionUseCase,
    FindAllDonationPointsUseCase findAllDonationPointsUseCase,
    UpdateDonationPointUseCase updateDonationPointUseCase,
    DeleteDonationPointUseCase deleteDonationPointUseCase
  ) {
    this.createDonationPointUseCase = createDonationPointUseCase;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
    this.findDonationPointByDescriptionUseCase = findDonationPointByDescriptionUseCase;
    this.findAllDonationPointsUseCase = findAllDonationPointsUseCase;
    this.updateDonationPointUseCase = updateDonationPointUseCase;
    this.deleteDonationPointUseCase = deleteDonationPointUseCase;
  }

  private DonationPointDto mapToDto(DonationPoint entity) {
    var user = entity.getUser();
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
    return new DonationPointDto(entity.getId(), userDto, entity.getDescription());
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Cadastra um novo ponto de doação", method = "POST")
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Ponto criado com sucesso"),
    @ApiResponse(responseCode = "400",
      description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(responseCode = "500",
      description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    )
  })
  public ResponseEntity<ApiSuccessResponse<DonationPointDto>> create(@RequestBody @Valid
                                                                     CreateDonationPointCommand command) {
    var entity = createDonationPointUseCase.execute(command);
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "Ponto criado com sucesso", mapToDto(entity));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Busca ponto de doação por ID", method = "GET")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Ponto encontrado"),
    @ApiResponse(
      responseCode = "404",
      description = "Ponto não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    )
  })
  public ResponseEntity<ApiSuccessResponse<DonationPointDto>> findById(@PathVariable("id") Long id,
                                                                       @RequestParam boolean isActive) {
    var entity = findDonationPointByIdUseCase.execute(id, isActive);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Ponto encontrado com sucesso", mapToDto(entity));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/description/{description}")
  @Operation(summary = "Busca ponto de doação por descrição", method = "GET")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Ponto encontrado"),
    @ApiResponse(
      responseCode = "404",
      description = "Ponto não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    )
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonationPointDto>>> findByDescription(
    @PathVariable("description") String description,
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize,
    @RequestParam boolean isActive
  ) {
    var page = findDonationPointByDescriptionUseCase.execute(
      description,
      pageNumber,
      pageSize,
      isActive
    );

    var items = page.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Ponto encontrado com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(summary = "Busca paginada de pontos de doação", method = "GET")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
    @ApiResponse(
      responseCode = "500",
      description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    )
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonationPointDto>>> findAll(
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize,
    @RequestParam boolean isActive) {
    var page = findAllDonationPointsUseCase.execute(pageNumber, pageSize, isActive);
    var items = page.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de pontos retornada com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Atualiza parcialmente um ponto de doação", method = "PATCH")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Ponto atualizado com sucesso"),
    @ApiResponse(
      responseCode = "400",
      description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Ponto não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    )
  })
  public ResponseEntity<ApiSuccessResponse<DonationPointDto>> update(
    @PathVariable("id") Long id,
    @RequestBody @Valid UpdateDonationPointCommand command) {
    var entity = updateDonationPointUseCase.execute(id, command);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Ponto atualizado com sucesso", mapToDto(entity));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um ponto de doação", method = "DELETE")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Ponto deletado com sucesso"),
    @ApiResponse(
      responseCode = "404",
      description = "Ponto não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(
      responseCode = "500",
      description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class))
    )
  })
  public ResponseEntity<ApiSuccessResponse<Void>> delete(@PathVariable("id") Long id) {
    deleteDonationPointUseCase.execute(id);
    var response = new ApiSuccessResponse<Void>(HttpStatus.OK, "Ponto deletado com sucesso", null);
    return ResponseEntity.ok(response);
  }
}
