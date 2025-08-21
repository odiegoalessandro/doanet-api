package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.commands.UpdateDonationItemsCommand;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.entities.donation.DonationItem;
import com.doanet.api.domain.validator.DonationStatusValidator;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UpdateDonationItemsUseCase {
  private final DonationRepository donationRepository;
  private final FindDonationByIdUseCase findDonationByIdUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;

  public UpdateDonationItemsUseCase(
    DonationRepository donationRepository,
    FindDonationByIdUseCase findDonationByIdUseCase,
    FindItemByIdUseCase findItemByIdUseCase
  ) {
    this.donationRepository = donationRepository;
    this.findDonationByIdUseCase = findDonationByIdUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
  }

  public Donation execute(Long id, UpdateDonationItemsCommand donationCommand) {
    var donation = this.findDonationByIdUseCase.execute(id);

    DonationStatusValidator.validate(donation.getStatus());

    var existingItems = donation.getDonationItems();
    Map<Long, DonationItem> existingItemsMap = existingItems.stream()
      .collect(
        Collectors.toMap(
          donationItem -> donationItem.getItem().getId(),
          Function.identity()
        ));

    for (var itemCommand : donationCommand.items()) {
      Long productId = itemCommand.itemId();
      int quantity = itemCommand.quantity();
      DonationItem existingItem = existingItemsMap.get(productId);

      if (quantity == 0 && existingItem != null) {
        existingItems.remove(existingItem);
      } else if (existingItem != null) {
        existingItem.setQuantity(quantity);
      } else {
        var item = findItemByIdUseCase.execute(productId);
        existingItems.add(new DonationItem(null, quantity, item, donation));
      }
    }

    return this.donationRepository.save(donation);
  }
}
