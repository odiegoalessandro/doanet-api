package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateRequestCommand;
import com.doanet.api.application.commands.UpdateRequestItemsCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.request.*;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.enums.RequestStatus;
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

import java.util.List;

@RestController
@RequestMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Solicitações", description = "Operações relacionadas as solicitações")
public class RequestController {

  private final CreateRequestUseCase createRequestUseCase;
  private final FindAllRequestsUseCase findAllRequestsUseCase;
  private final FindRequestByDonationPointIdUseCase findRequestByDonationPointIdUseCase;
  private final FindRequestByIdUseCase findRequestByIdUseCase;
  private final FindRequestByOngIdUseCase findRequestByOngIdUseCase;
  private final FindRequestByStatusUseCase findRequestByStatusUseCase;
  private final UpdateRequestItemUseCase updateRequestItemUseCase;
  private final UpdateRequestStatusUseCase updateRequestStatusUseCase;

  public RequestController(CreateRequestUseCase createRequestUseCase,
                           FindAllRequestsUseCase findAllRequestsUseCase,
                           FindRequestByDonationPointIdUseCase findRequestByDonationPointIdUseCase,
                           FindRequestByIdUseCase findRequestByIdUseCase,
                           FindRequestByOngIdUseCase findRequestByOngIdUseCase,
                           FindRequestByStatusUseCase findRequestByStatusUseCase,
                           UpdateRequestItemUseCase updateRequestItemUseCase,
                           UpdateRequestStatusUseCase updateRequestStatusUseCase) {
    this.createRequestUseCase = createRequestUseCase;
    this.findAllRequestsUseCase = findAllRequestsUseCase;
    this.findRequestByDonationPointIdUseCase = findRequestByDonationPointIdUseCase;
    this.findRequestByIdUseCase = findRequestByIdUseCase;
    this.findRequestByOngIdUseCase = findRequestByOngIdUseCase;
    this.findRequestByStatusUseCase = findRequestByStatusUseCase;
    this.updateRequestItemUseCase = updateRequestItemUseCase;
    this.updateRequestStatusUseCase = updateRequestStatusUseCase;
  }

  @ApiResponses({@ApiResponse(responseCode = "201",
    description = "Solicitação criada com sucesso",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "400",
      description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Cria um novo pedido")
  public ResponseEntity<ApiSuccessResponse<RequestDto>> create(@RequestBody @Valid CreateRequestCommand command) {
    var result = createRequestUseCase.execute(command);
    var response = new ApiSuccessResponse<>(HttpStatus.CREATED, "Pedido criado com sucesso", mapToDto(result));
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
    description = "Solicitação encontrada",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "404",
      description = "Solicitação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @GetMapping("/{id}")
  @Operation(summary = "Busca pedido por ID")
  public ResponseEntity<ApiSuccessResponse<RequestDto>> findById(@PathVariable("id") Long id) {
    var result = findRequestByIdUseCase.execute(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Pedido encontrado com sucesso", mapToDto(result));
    return ResponseEntity.ok(response);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
    description = "Lista de solicitações retornada",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @GetMapping
  @Operation(summary = "Lista todos os pedidos paginados")
  public ResponseEntity<ApiSuccessResponse<PageResponse<RequestDto>>> findAll(@RequestParam int pageNumber,
                                                                              @RequestParam int pageSize) {
    var page = findAllRequestsUseCase.execute(pageNumber, pageSize);
    var items = page.content().stream().map(this::mapToDto).toList();
    var
      response =
      new ApiSuccessResponse<>(HttpStatus.OK,
        "Lista de pedidos retornada com sucesso",
        new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage()));
    return ResponseEntity.ok(response);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
    description = "Lista de solicitações retornada",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @GetMapping("/donation-point/{donationPointId}")
  @Operation(summary = "Busca pedidos por ID do ponto de doação")
  public ResponseEntity<ApiSuccessResponse<PageResponse<RequestDto>>> findByDonationPointId(
    @PathVariable Long donationPointId, @RequestParam int pageNumber, @RequestParam int pageSize) {
    var page = findRequestByDonationPointIdUseCase.execute(donationPointId, pageNumber, pageSize);
    var items = page.content().stream().map(this::mapToDto).toList();
    var
      response =
      new ApiSuccessResponse<>(HttpStatus.OK,
        "Pedidos encontrados",
        new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage()));
    return ResponseEntity.ok(response);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
    description = "Lista de solicitações retornada",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @GetMapping("/ong/{ongId}")
  @Operation(summary = "Busca pedidos por ID da ONG")
  public ResponseEntity<ApiSuccessResponse<PageResponse<RequestDto>>> findByOngId(@PathVariable Long ongId,
                                                                                  @RequestParam int pageNumber,
                                                                                  @RequestParam int pageSize) {
    var page = findRequestByOngIdUseCase.execute(ongId, pageNumber, pageSize);
    var items = page.content().stream().map(this::mapToDto).toList();
    var
      response =
      new ApiSuccessResponse<>(HttpStatus.OK,
        "Pedidos encontrados",
        new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage()));
    return ResponseEntity.ok(response);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
    description = "Solicitações encontradas",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "400",
      description = "Parâmetros inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @GetMapping("/status")
  @Operation(summary = "Busca solicitações por status")
  public ResponseEntity<ApiSuccessResponse<PageResponse<RequestDto>>> findByStatus(@RequestParam RequestStatus status,
                                                                                   @RequestParam int pageNumber,
                                                                                   @RequestParam int pageSize) {
    var page = findRequestByStatusUseCase.execute(status, pageNumber, pageSize);
    var items = page.content().stream().map(this::mapToDto).toList();
    var
      response =
      new ApiSuccessResponse<>(HttpStatus.OK,
        "Pedidos encontrados",
        new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage()));
    return ResponseEntity.ok(response);
  }

  @ApiResponses(value = {@ApiResponse(responseCode = "200",
    description = "Solicitação atualizada com sucesso",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "404",
      description = "Solicitação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @PatchMapping("/{id}")
  @Operation(summary = "Atualiza itens do pedido")
  public ResponseEntity<ApiSuccessResponse<RequestDto>> updateItems(@PathVariable Long id,
                                                                    @RequestBody @Valid
                                                                    UpdateRequestItemsCommand command) {
    var result = updateRequestItemUseCase.execute(id, command);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Itens atualizados com sucesso", mapToDto(result));
    return ResponseEntity.ok(response);
  }

  @ApiResponses(value = {@ApiResponse(responseCode = "200",
    description = "Solicitação atualizada com sucesso",
    content = @Content(schema = @Schema(implementation = ApiSuccessResponse.class))),
    @ApiResponse(responseCode = "404",
      description = "Solicitação não encontrada",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500",
      description = "Erro interno do servidor",
      content = @Content(schema = @Schema(implementation = ApiError.class)))})
  @PatchMapping("/status/{id}")
  @Operation(summary = "Atualiza status do pedido")
  public ResponseEntity<ApiSuccessResponse<RequestDto>> updateStatus(@PathVariable Long id,
                                                                     @RequestParam RequestStatus status) {
    var result = updateRequestStatusUseCase.execute(id, status);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Status atualizado com sucesso", mapToDto(result));
    return ResponseEntity.ok(response);
  }

  private RequestDto mapToDto(Request entity) {
    List<RequestItemDto>
      items =
      entity.getItems()
        .stream()
        .map(item -> new RequestItemDto(item.getId(), item.getItem().getId(), item.getQuantity()))
        .toList();
    return new RequestDto(entity.getId(),
      entity.getOng().getId(),
      entity.getDonationPoint().getId(),
      items,
      entity.getStatus());
  }
}

