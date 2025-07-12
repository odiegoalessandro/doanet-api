package com.doanet.api.service;

import com.doanet.api.dto.UpdateRequestItemDto;
import com.doanet.api.dto.UpdateRequestItemsDto;
import com.doanet.api.entity.Request;
import com.doanet.api.entity.RequestItem;
import com.doanet.api.enums.Status;
import com.doanet.api.repository.RequestItemRepository;
import com.doanet.api.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class UpdateRequestService {
  private static final Set<Status> NOT_ALLOWED_TO_UPDATE = Set.of(Status.BLOCKED, Status.DELIVERED, Status.EXPIRED);

  private FindItemService findItemService;
  private RequestRepository requestRepository;

  public UpdateRequestService(
    FindItemService findItemService,
    RequestRepository requestRepository,
    RequestItemRepository requestItemRepository
  ) {
    this.findItemService = findItemService;
    this.requestRepository = requestRepository;
  }

  public Request updateStatus(Long requestId, Status status){
    Objects.requireNonNull(status, "Status não pode ser nulo");

    var request = this.requestRepository.findById(requestId)
      .orElseThrow(() -> new EntityNotFoundException("Request não foi encontrada"));

    if (NOT_ALLOWED_TO_UPDATE.contains(request.getStatus())) {
      throw new IllegalStateException("Não foi possível alterar o status desta solicitação. " +
        "Solicitações finalizadas ou em estado inválido não podem ser modificadas. Verifique o status atual.");
    }

    request.setStatus(status);

    return this.requestRepository.save(request);
  }

  public Request updateItems(Long requestId, UpdateRequestItemsDto updateRequestDto){
    var request = this.requestRepository.findById(requestId)
      .orElseThrow(() -> new EntityNotFoundException("Request não foi encontrada"));

    if(request.getStatus() != Status.CREATED){
      throw new IllegalStateException("Não foi possivel alterar a solicitação. " +
        "Solicitações que possuem alguma movimentação em aberto não podem ser alteradas");
    }

    var items = request.getRequestItems();

    for(UpdateRequestItemDto dto : updateRequestDto.items()){
      var existing = items
        .stream()
        .filter(i -> i.getId() == dto.itemId())
        .findFirst();

      if(dto.quantity() == 0){
        existing.ifPresent(items::remove);

        continue;
      }

      if(existing.isPresent()){
        existing.get().setQuantity(dto.quantity());
      } else {
        var item = this.findItemService.findById(dto.itemId());
        var newItem = new RequestItem(request, item, dto.quantity());

        items.add(newItem);
      }
    }

    return this.requestRepository.save(request);
  }
}
