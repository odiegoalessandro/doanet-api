package com.doanet.api.application.usecases.donation;

import com.doanet.api.application.commands.CreateDonationCommand;
import com.doanet.api.application.gateways.DonationRepository;
import com.doanet.api.application.usecases.donationpoint.FindActiveDonationPointByIdUseCase;
import com.doanet.api.application.usecases.donor.FindActiveDonorByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.domain.entities.donation.Donation;
import com.doanet.api.domain.entities.donation.DonationItem;
import com.doanet.api.domain.enums.DonationStatus;

import java.time.LocalDate;

public class CreateDonationUseCase {
  private final DonationRepository donationRepository;
  private final FindActiveDonorByIdUseCase findActiveDonorByIdUseCase;
  private final FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase;
  private final FindItemByIdUseCase findItemByIdUseCase;

  public CreateDonationUseCase(DonationRepository donationRepository,
                               FindActiveDonorByIdUseCase findActiveDonorByIdUseCase,
                               FindActiveDonationPointByIdUseCase findActiveDonationPointByIdUseCase,
                               FindItemByIdUseCase findItemByIdUseCase) {
    this.donationRepository = donationRepository;
    this.findActiveDonorByIdUseCase = findActiveDonorByIdUseCase;
    this.findActiveDonationPointByIdUseCase = findActiveDonationPointByIdUseCase;
    this.findItemByIdUseCase = findItemByIdUseCase;
  }

  public Donation execute(CreateDonationCommand donationCommand){
    var donor = findActiveDonorByIdUseCase.execute(donationCommand.donorId());
    var donationPoint = findActiveDonationPointByIdUseCase.execute(donationCommand.donationPointId());
    var donation = new Donation(
      null, donor,
      donationPoint,
      LocalDate.now(),
      null,
      DonationStatus.CREATED
    );

    var donationItems = donationCommand.items().stream()
      .map(item -> {
        var itemEntity = findItemByIdUseCase.execute(item.productId());

        return new DonationItem(null, item.quantity(), itemEntity, donation);
      })
      .toList();

    donation.setDonationItems(donationItems);

    return this.donationRepository.save(donation);
  }
}
