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

@RestController
@RequestMapping(value = "/donor", produces = { "application/json" })
@Tag(name = "Doadores", description = "Operações relacionadas aos Doadores")
public class DonorController {

  private final CreateDonorUseCase createDonorUseCase;
  private final FindDonorByIdUseCase findDonorByIdUseCase;
  private final FindAllDonorsUseCase findAllDonorsUseCase;
  private final FindDonorByDocumentUseCase findDonorByDocumentUseCase;
  private final FindDonorByReasonSocialUseCase findDonorByReasonSocialUseCase;
  private final UpdateDonorUseCase updateDonorUseCase;
  private final DisableDonorUseCase disableDonorUseCase;

  public DonorController(CreateDonorUseCase createDonorUseCase,
                         FindDonorByIdUseCase findDonorByIdUseCase,
                         FindAllDonorsUseCase findAllDonorsUseCase,
                         FindDonorByDocumentUseCase findDonorByDocumentUseCase,
                         FindDonorByReasonSocialUseCase findDonorByReasonSocialUseCase,
                         UpdateDonorUseCase updateDonorUseCase,
                         DisableDonorUseCase disableDonorUseCase) {
    this.createDonorUseCase = createDonorUseCase;
    this.findDonorByIdUseCase = findDonorByIdUseCase;
    this.findAllDonorsUseCase = findAllDonorsUseCase;
    this.findDonorByDocumentUseCase = findDonorByDocumentUseCase;
    this.findDonorByReasonSocialUseCase = findDonorByReasonSocialUseCase;
    this.updateDonorUseCase = updateDonorUseCase;
    this.disableDonorUseCase = disableDonorUseCase;
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
  @Operation(summary = "Busca doador por ID", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador encontrado"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonorDto>> findById(@PathVariable("id") Long id, @RequestParam boolean isActive) {
    var donor = findDonorByIdUseCase.execute(id, isActive);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador encontrado com sucesso", mapToDto(donor));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/document/{document}")
  @Operation(summary = "Busca doador por documento", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador encontrado"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<DonorDto>> findByDocument(@PathVariable("document") String document,
                                                                     @RequestParam boolean isActive) {
    var donor = findDonorByDocumentUseCase.execute(document, isActive);
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
    @RequestParam Integer pageSize,
    @RequestParam boolean isActive
  ) {

    var page = findDonorByReasonSocialUseCase.execute(reasonSocial, pageNumber, pageSize, isActive);
    var items = page.content().stream().map(this::mapToDto).toList();
    var dtoPage = new PageResponse<>(items, page.totalElements(), page.totalPages(), page.currentPage());
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Lista de doadores retornada com sucesso", dtoPage);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @Operation(summary = "Busca paginada de doadores", method = "GET")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<PageResponse<DonorDto>>> findAll(
    @RequestParam Integer pageNumber,
    @RequestParam Integer pageSize,
    @RequestParam boolean isActive
  ) {
    var page = findAllDonorsUseCase.execute(pageNumber, pageSize, isActive);
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
    @RequestBody @Valid UpdateDonorCommand updateDonorCommand,
    @RequestParam boolean isActive
  ) {
    var donor = updateDonorUseCase.execute(id, updateDonorCommand, isActive);
    var response = new ApiSuccessResponse<>(HttpStatus.OK, "Doador atualizado com sucesso", mapToDto(donor));
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/disable/{id}")
  @Operation(summary = "Desativa um doador", method = "PATCH")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Doador desativado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Doador não encontrado",
      content = @Content(schema = @Schema(implementation = ApiError.class))),
    @ApiResponse(responseCode = "500", description = "Erro interno",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  })
  public ResponseEntity<ApiSuccessResponse<Void>> disable(@PathVariable("id") Long id) {
    disableDonorUseCase.execute(id);
    var response = new ApiSuccessResponse<Void>(HttpStatus.OK, "Doador desativado com sucesso", null);
    return ResponseEntity.ok(response);
  }
}
