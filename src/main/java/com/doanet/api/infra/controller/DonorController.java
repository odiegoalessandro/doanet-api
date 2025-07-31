package com.doanet.api.infra.controller;

import com.doanet.api.application.commands.CreateDonorCommand;
import com.doanet.api.application.commands.UpdateDonorCommand;
import com.doanet.api.application.dto.PageResponse;
import com.doanet.api.application.usecases.donor.*;
import com.doanet.api.domain.entities.donor.Donor;
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

// TODO: Modificar as pesquisas por ID e findAll para retornar pontos ativos e inativos conforme o usuário passe via query param
@RestController
@RequestMapping(value = "/donor", produces = { "application/json" })
@Tag(name = "Doadores", description = "Operações relacionadas aos Doadores")
public class DonorController {

  private final CreateDonorUseCase createDonorUseCase;
  private final FindActiveDonorByIdUseCase findActiveDonorByIdUseCase;
  private final FindAllActiveDonorsUseCase findAllActiveDonorsUseCase;
  private final FindActiveDonorByDocumentUseCase findActiveDonorByDocumentUseCase;
  private final FindDonorByReasonSocialUseCase findDonorByReasonSocialUseCase;
  private final UpdateDonorUseCase updateDonorUseCase;
  private final DeleteDonorUseCase deleteDonorUseCase;

  public DonorController(CreateDonorUseCase createDonorUseCase,
                         FindActiveDonorByIdUseCase findActiveDonorByIdUseCase,
                         FindAllActiveDonorsUseCase findAllActiveDonorsUseCase,
                         FindActiveDonorByDocumentUseCase findActiveDonorByDocumentUseCase,
                         FindDonorByReasonSocialUseCase findDonorByReasonSocialUseCase,
                         UpdateDonorUseCase updateDonorUseCase,
                         DeleteDonorUseCase deleteDonorUseCase) {
    this.createDonorUseCase = createDonorUseCase;
    this.findActiveDonorByIdUseCase = findActiveDonorByIdUseCase;
    this.findAllActiveDonorsUseCase = findAllActiveDonorsUseCase;
    this.findActiveDonorByDocumentUseCase = findActiveDonorByDocumentUseCase;
    this.findDonorByReasonSocialUseCase = findDonorByReasonSocialUseCase;
    this.updateDonorUseCase = updateDonorUseCase;
    this.deleteDonorUseCase = deleteDonorUseCase;
  }

  private DonorDto mapToDto(Donor donor) {
    var user = donor.getUser();
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
    return new DonorDto(donor.getId(), donor.getDocument(), donor.getReasonSocial(), userDto);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Cadastra um novo doador", method = "POST")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Doador criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonorDto>> create(@RequestBody @Valid CreateDonorCommand donor) {
    var donorEntity = createDonorUseCase.execute(donor);
    var response = new ApiSuccessResponse<>(
      HttpStatus.CREATED,
      "Doador criado com sucesso",
      mapToDto(donorEntity)
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Busca doador ativo por ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador encontrado"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonorDto>> findById(@PathVariable("id") Long id) {
    var donor = findActiveDonorByIdUseCase.execute(id);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador encontrado com sucesso", mapToDto(donor));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/document/{document}")
  @Operation(summary = "Busca doador ativo por documento", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador encontrado"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonorDto>> findByDocument(@PathVariable("document") String document) {
    var donor = findActiveDonorByDocumentUseCase.execute(document);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador encontrado com sucesso", mapToDto(donor));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/reason-social/{reasonSocial}")
  @Operation(summary = "Busca doador por razão social", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador encontrado"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonorDto>>> findByReasonSocial(
    @PathVariable("reasonSocial") String reasonSocial,
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize
  ) {

    var page = findDonorByReasonSocialUseCase.execute(reasonSocial, pageNumber, pageSize);
    var items = page.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de doadores retornada com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(summary = "Busca paginada de doadores ativos", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonorDto>>> findAll(
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize
  ) {
    var page = findAllActiveDonorsUseCase.execute(pageNumber, pageSize);
    var items = page.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de doadores retornada com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Atualiza parcialmente um doador", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador atualizado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Dados inválidos",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonorDto>> update(
    @PathVariable("id") Long id,
    @RequestBody @Valid UpdateDonorCommand updateDonorCommand
  ) {
    var donor = updateDonorUseCase.execute(id, updateDonorCommand);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador atualizado com sucesso", mapToDto(donor));
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Deleta um doador", method = "DELETE")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador deletado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Void>> delete(@PathVariable("id") Long id) {
    deleteDonorUseCase.execute(id);
    var response = new ApiSuccessResponse<Void>(HttpStatus.OK, "Doador deletado com sucesso", null);
    return ResponseEntity.ok(response);
  }
}
