package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.commands.CreateDonationCommand;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.donationpoint.FindDonationPointByIdUseCase;
import com.doanet.api.application.usecases.donor.FindDonorByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.entities.donation.DonationItem;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.DonationStatus;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateDonationUseCase {
  private final DonationRepository donationRepository;
  private final FindDonorByIdUseCase findDonorByIdUseCase;
  private final FindDonationPointByIdUseCase findDonationPointByIdUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public CreateDonationUseCase(
      DonationRepository donationRepository,
      FindDonorByIdUseCase findDonorByIdUseCase,
      FindDonationPointByIdUseCase findDonationPointByIdUseCase,
      FindItemByIdUseCase findItemByIdUseCase,
      RecordAuditUseCase recordAuditUseCase) {
    this.donationRepository = donationRepository;
    this.findDonorByIdUseCase = findDonorByIdUseCase;
    this.findDonationPointByIdUseCase = findDonationPointByIdUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Donation execute(CreateDonationCommand donationCommand) {
    var donor = findDonorByIdUseCase.execute(donationCommand.donorId(), true);
    var donationPoint =
        findDonationPointByIdUseCase.execute(donationCommand.donationPointId(), true);

    if (!donor.getUser().isActive() || !donationPoint.getUser().isActive()) {
      throw new IllegalStateException(
          "Não foi possivel realizar a doação. Doador ou ponto de doação não estão " + "ativos.");
    }

    var donation =
        new Donation(null, donor, donationPoint, LocalDate.now(), null, DonationStatus.CREATED);

    var donationItems =
        donationCommand.items().stream()
            .map(
                item -> {
                  var itemEntity = findItemByIdUseCase.execute(item.productId());

                  return new DonationItem(null, item.quantity(), itemEntity, donation);
                })
            .toList();

    donation.setDonationItems(donationItems);

    var savedDonation = this.donationRepository.save(donation);

    this.recordAuditUseCase.execute(
        AuditAction.CREATE,
        AuditedEntity.DONATION,
        savedDonation.getId(),
        null,
        this.auditStateOf(savedDonation));

    return savedDonation;
  }

  private Map<String, Object> auditStateOf(Donation donation) {
    var state = new LinkedHashMap<String, Object>();
    state.put("donorId", donation.getDonor().getId());
    state.put("donationPointId", donation.getDonationPoint().getId());
    state.put("createdAt", donation.getCreatedAt());
    state.put("status", donation.getStatus());
    state.put(
        "items",
        donation.getDonationItems().stream()
            .map(
                donationItem ->
                    Map.of(
                        "itemId", donationItem.getItem().getId(),
                        "quantity", donationItem.getQuantity()))
            .toList());

    return state;
  }
}
