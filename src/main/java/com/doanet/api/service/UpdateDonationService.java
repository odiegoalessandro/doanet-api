package com.doanet.api.service;

import com.doanet.api.dto.UpdateDonationItemDto;
import com.doanet.api.dto.UpdateDonationItemsDto;
import com.doanet.api.entity.Donation;
import com.doanet.api.entity.DonationItem;
import com.doanet.api.enums.Status;
import com.doanet.api.repository.DonationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class UpdateDonationService {
  private static final Set<Status> NOT_ALLOWED_TO_UPDATE = Set.of(Status.BLOCKED, Status.DELIVERED, Status.EXPIRED);

  private FindItemService findItemService;
  private DonationRepository donationRepository;

  public UpdateDonationService(
    FindItemService findItemService,
    DonationRepository donationRepository
  ) {
    this.findItemService = findItemService;
    this.donationRepository = donationRepository;
  }

  public Donation updateStatus(Long donationId, Status status){
    Objects.requireNonNull(status, "Status não pode ser nulo");

    var donation = this.donationRepository.findById(donationId)
      .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));

    if (NOT_ALLOWED_TO_UPDATE.contains(donation.getStatus())) {
      throw new IllegalStateException("Não foi possível alterar o status desta doação. " +
        "Doações finalizadas ou em estado inválido não podem ser modificadas. Verifique o status atual.");
    }

    donation.setStatus(status);

    return this.donationRepository.save(donation);
  }

  public Donation updateItems(Long donationId, UpdateDonationItemsDto updateDonationDto) {
    var donation = this.donationRepository.findById(donationId)
      .orElseThrow(() -> new EntityNotFoundException("Doação não encontrada"));

    if(donation.getStatus() != Status.CREATED){
      throw new IllegalStateException("Não foi possivel alterar a doação. " +
        "Doações que possuem alguma movimentação em aberto não podem ser alteradas");
    }

    var items = donation.getDonationItems();

    for (UpdateDonationItemDto dto : updateDonationDto.items()) {
      var existing = items.stream()
        .filter(i -> i.getId().equals(dto.itemId()))
        .findFirst();

      if (dto.quantity() == 0) {
        existing.ifPresent(items::remove);
        continue;
      }

      if (existing.isPresent()) {
        existing.get().setQuantity(dto.quantity());
      } else {
        var product = findItemService.findById(dto.itemId());
        var newItem = new DonationItem(product, donation,dto.quantity());

        items.add(newItem);
      }
    }

    return donationRepository.save(donation);
  }
}
