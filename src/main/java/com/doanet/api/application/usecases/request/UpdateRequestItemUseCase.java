package com.doanet.api.application.usecases.request;

import com.doanet.api.application.commands.UpdateRequestItemsCommand;
import com.doanet.api.application.gateways.RequestRepository;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.domain.entities.request.Request;
import com.doanet.api.domain.entities.request.RequestItem;
import com.doanet.api.domain.validator.RequestStatusValidator;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UpdateRequestItemUseCase {
  private final RequestRepository requestRepository;
  private final FindRequestByIdUseCase findRequestByIdUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;

  public UpdateRequestItemUseCase(RequestRepository requestRepository,
                                  FindRequestByIdUseCase findRequestByIdUseCase,
                                  FindItemByIdUseCase findItemByIdUseCase) {
    this.requestRepository = requestRepository;
    this.findRequestByIdUseCase = findRequestByIdUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
  }

  public Request execute(Long id, UpdateRequestItemsCommand requestCommand) {
    var request = this.findRequestByIdUseCase.execute(id);

    RequestStatusValidator.validate(request.getStatus());

    var existingItems = request.getItems();
    Map<Long, RequestItem> existingItemsMap = existingItems.stream()
      .collect(Collectors.toMap(
        requestItem -> requestItem.getItem().getId(),
        Function.identity()
      ));

    for (var itemCommand : requestCommand.items()) {
      Long productId = itemCommand.itemId();
      int quantity = itemCommand.quantity();
      RequestItem existingItem = existingItemsMap.get(productId);

      if (quantity == 0 && existingItem != null) {
        existingItems.remove(existingItem);
      } else if (existingItem != null) {
        existingItem.setQuantity(quantity);
      } else {
        var item = findItemByIdUseCase.execute(productId);
        existingItems.add(new RequestItem(null, request, item, quantity));
      }
    }

    return this.requestRepository.save(request);
  }
}
