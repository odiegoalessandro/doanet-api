package com.doanet.api.application.usecases.item;

import com.doanet.api.application.commands.CreateItemCommand;
import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.domain.entities.item.Item;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateItemUseCase {
  private final ItemRepository itemRepository;
  private final RecordAuditUseCase recordAuditUseCase;

  public CreateItemUseCase(ItemRepository itemRepository, RecordAuditUseCase recordAuditUseCase) {
    this.itemRepository = itemRepository;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Item execute(CreateItemCommand itemCommand) {
    var item =
        new Item(
            null,
            itemCommand.name(),
            itemCommand.description(),
            itemCommand.isPerishable(),
            LocalDate.now());

    var savedItem = itemRepository.save(item);

    this.recordAuditUseCase.execute(
        AuditAction.CREATE,
        AuditedEntity.ITEM,
        savedItem.getId(),
        null,
        this.auditStateOf(savedItem));

    return savedItem;
  }

  private Map<String, Object> auditStateOf(Item item) {
    var state = new LinkedHashMap<String, Object>();
    state.put("name", item.getName());
    state.put("description", item.getDescription());
    state.put("perishable", item.isPerishable());
    state.put("expirationDate", item.getExpirationDate());

    return state;
  }
}
