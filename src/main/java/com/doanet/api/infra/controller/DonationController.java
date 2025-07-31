package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateDonationCommand;
import com.doanet.api.application.commands.UpdateDonationItemsCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.donation.*;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.enums.DonationStatus;
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

@RestController
@RequestMapping(value = "/donation", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Doações", description = "Operações relacionadas às doações")
public class DonationController {
  private final CreateDonationUseCase createDonationUseCase;
  private final FindDonationByIdUseCase findDonationByIdUseCase;
  private final FindDonationByDonationPointIdUseCase findDonationByDonationPointIdUseCase;
  private final FindDonationByDonorIdUseCase findDonationByDonorIdUseCase;
  private final FindDonationByStatusUseCase findDonationByStatusUseCase;
  private final FindAllDonationsUseCase findAllDonationsUseCase;
  private final UpdateDonationItemsUseCase updateDonationItemsUseCase;
  private final UpdateStatusDonationUseCase updateStatusDonationUseCase;

  public DonationController(CreateDonationUseCase createDonationUseCase,
                            FindDonationByIdUseCase findDonationByIdUseCase,
                            FindDonationByDonationPointIdUseCase findDonationByDonationPointIdUseCase,
                            FindDonationByDonorIdUseCase findDonationByDonorIdUseCase,
                            FindDonationByStatusUseCase findDonationByStatusUseCase,
                            FindAllDonationsUseCase findAllDonationsUseCase,
                            UpdateDonationItemsUseCase updateDonationItemsUseCase,
                            UpdateStatusDonationUseCase updateStatusDonationUseCase) {
    this.createDonationUseCase = createDonationUseCase;
    this.findDonationByIdUseCase = findDonationByIdUseCase;
    this.findDonationByDonationPointIdUseCase = findDonationByDonationPointIdUseCase;
    this.findDonationByDonorIdUseCase = findDonationByDonorIdUseCase;
    this.findDonationByStatusUseCase = findDonationByStatusUseCase;
    this.findAllDonationsUseCase = findAllDonationsUseCase;
    this.updateDonationItemsUseCase = updateDonationItemsUseCase;
    this.updateStatusDonationUseCase = updateStatusDonationUseCase;
  }

  @Operation(summary = "Realiza o cadastro da doação", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Doação criada com sucesso",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiSuccessResponse<DonationDto>> create(
    @RequestBody
    @Valid
    CreateDonationCommand createDonationCommand
  ) {
    var result = this.createDonationUseCase.execute(createDonationCommand);
    var response = new ApiSuccessResponse<DonationDto>(
      HttpStatus.CREATED,
      "Doação criada com sucesso",
      mapToDto(result)
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @Operation(summary = "Pesquisa doação pelo ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Doação encontrada",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<DonationDto>> findById(
    @PathVariable("id")
    @Parameter(name = "id", description = "Id da doação a ser buscada", example = "1")
    Long id
  ) {
    var result = this.findDonationByIdUseCase.execute(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doação encontrada com sucesso", mapToDto(result));

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa todas as doações paginadas", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Lista de doações retornada",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonationDto>>> findAll(
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var page = this.findAllDonationsUseCase.execute(pageNumber, pageSize);
    var items = page.content().stream()
      .map(this::mapToDto)
      .toList();

    var result = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de doações retornada com sucesso", result);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa doações pelo ID do doador", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Lista de doações retornada",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/donor/{donorId}")
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonationDto>>> findByDonorId(
    @PathVariable("donorId")
    @Parameter(name = "donorId", description = "Id do doador", example = "1")
    Long donorId,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var page = this.findDonationByDonorIdUseCase.execute(donorId, pageNumber, pageSize);
    var items = page.content().stream()
      .map(this::mapToDto)
      .toList();

    var result = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de doações por doador retornada com sucesso",
      result
    );

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa doações pelo ID do ponto de doação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Lista de doações retornada",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  @GetMapping("/donation-point/{donationPointId}")
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonationDto>>> findByDonationPointId(
    @PathVariable("donationPointId")
    @Parameter(name = "donationPointId", description = "Id do ponto de doação", example = "1")
    Long donationPointId,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var page = this.findDonationByDonationPointIdUseCase.execute(
      donationPointId,
      pageNumber,
      pageSize
    );
    var items = page.content().stream()
      .map(this::mapToDto)
      .toList();

    var result = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de doações por ponto de doação retornada com sucesso",
      result
    );
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Pesquisa doações pelo status da doação", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Lista de doações retornada",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonationDto>>> findByStatus(
    @RequestParam
    @Parameter(name = "status", description = "Status da doação", example = "PENDING")
    DonationStatus status,
    @RequestParam
    @Parameter(name = "pageNumber", description = "Número da página", example = "0")
    int pageNumber,
    @RequestParam
    @Parameter(name = "pageSize", description = "Tamanho da página", example = "50")
    int pageSize
  ) {
    var page = this.findDonationByStatusUseCase.execute(status, pageNumber, pageSize);
    var items = page.content().stream()
      .map(this::mapToDto)
      .toList();

    var result = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Lista de doações por status retornada com sucesso",
      result
    );

    return ResponseEntity.ok(response);
  }

  @PatchMapping("/status/{id}")
  @Operation(description = "Atualiza os status da doação", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Doação atualizada com sucesso",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonationDto>> updateStatus(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da doação a ser atualizada")
    Long id,

    @RequestParam("status")
    @Parameter(name = "status", description = "Novo status da doação")
    DonationStatus status
  ) {
    var donation = this.updateStatusDonationUseCase.execute(id, status);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Doação teve seu status atualizado com sucesso",
      mapToDto(donation)
    );

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }


  @Operation(description = "Atualiza os itens da doação", method = "PATCH")
  @PatchMapping("/{id}")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Doação atualizada com sucesso",
      content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))
    ),
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonationDto>> updateItems(
    @PathVariable("id")
    @Parameter(name = "id", description = "ID da doação a ser atualizada")
    Long id,
    @RequestBody
    UpdateDonationItemsCommand updateDonationItemsCommand
  ){
    var donation = this.updateDonationItemsUseCase.execute(id, updateDonationItemsCommand);
    var response = new ApiSuccessResponse<>(
      HttpStatus.OK,
      "Doação teve seus itens atualizados com sucesso",
      mapToDto(donation)
    );

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  private DonationDto mapToDto(Donation entity) {
    var items = entity.getDonationItems().stream()
      .map(item -> new DonationItemDto(item.getId(), item.getItem().getId(), item.getQuantity()))
      .toList();

    return new DonationDto(
      entity.getId(),
      entity.getDonor().getId(),
      entity.getDonationPoint().getId(),
      items,
      entity.getStatus()
    );
  }
}
