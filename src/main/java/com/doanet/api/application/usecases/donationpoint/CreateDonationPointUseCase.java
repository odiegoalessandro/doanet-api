package com.doanet.api.application.usecases.donationpoint;

import com.doanet.api.application.commands.CreateDonationPointCommand;
import com.doanet.api.application.gateways.DonationPointRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.donationpoint.DonationPoint;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.UserType;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreateDonationPointUseCase {
  private final DonationPointRepository donationPointRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;
  private final PasswordHasher passwordHasher;
  private final RecordAuditUseCase recordAuditUseCase;

  public CreateDonationPointUseCase(
    DonationPointRepository donationPointRepository,
    GeolocateUserUseCase geolocateUserUseCase,
    PasswordHasher passwordHasher,
    RecordAuditUseCase recordAuditUseCase
  ) {
    this.donationPointRepository = donationPointRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
    this.passwordHasher = passwordHasher;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public DonationPoint execute(CreateDonationPointCommand donationPointCommand){
    var user = new User(
      null,
      donationPointCommand.name(),
      donationPointCommand.email(),
      donationPointCommand.password(),
      donationPointCommand.phone(),
      donationPointCommand.street(),
      donationPointCommand.number(),
      donationPointCommand.neighborhood(),
      donationPointCommand.city(),
      donationPointCommand.state(),
      donationPointCommand.zipCode(),
      null,
      null,
      UserType.DONATION_POINT,
      true
    );

    user.setPassword(this.passwordHasher.hash(donationPointCommand.password()));

    this.geolocateUserUseCase.execute(user);

    var donationPoint = new DonationPoint(null, user, donationPointCommand.description());

    var savedDonationPoint = this.donationPointRepository.save(donationPoint);

    this.recordAuditUseCase.execute(
      AuditAction.CREATE,
      AuditedEntity.DONATION_POINT,
      savedDonationPoint.getId(),
      null,
      this.auditStateOf(savedDonationPoint)
    );

    return savedDonationPoint;
  }

  private Map<String, Object> auditStateOf(DonationPoint donationPoint) {
    var state = new LinkedHashMap<String, Object>();
    state.put("userId", donationPoint.getUser().getId());
    state.put("name", donationPoint.getUser().getName());
    state.put("email", donationPoint.getUser().getEmail());
    state.put("userType", donationPoint.getUser().getUserType());
    state.put("active", donationPoint.getUser().isActive());
    state.put("description", donationPoint.getDescription());

    return state;
  }
}
