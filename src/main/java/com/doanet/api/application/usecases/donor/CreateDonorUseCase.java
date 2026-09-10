package com.doanet.api.application.usecases.donor;

import com.doanet.api.application.commands.CreateDonorCommand;
import com.doanet.api.application.gateways.DonorRepository;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.donor.Donor;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.UserType;

import java.util.LinkedHashMap;
import java.util.Map;

public class CreateDonorUseCase {
  private final DonorRepository donorRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;
  private final RecordAuditUseCase recordAuditUseCase;

  public CreateDonorUseCase(DonorRepository donorRepository,
                            GeolocateUserUseCase geolocateUserUseCase,
                            RecordAuditUseCase recordAuditUseCase) {
    this.donorRepository = donorRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Donor execute(CreateDonorCommand donorCommand){
    var user = new User(
      null,
      donorCommand.name(),
      donorCommand.email(),
      donorCommand.password(),
      donorCommand.phone(),
      donorCommand.street(),
      donorCommand.number(),
      donorCommand.neighborhood(),
      donorCommand.city(),
      donorCommand.state(),
      donorCommand.zipCode(),
      null,
      null,
      UserType.DONOR,
      true
    );

    this.geolocateUserUseCase.execute(user);

    var donor = new Donor(null, user, donorCommand.document(), donorCommand.reasonSocial());

    var savedDonor = this.donorRepository.save(donor);

    this.recordAuditUseCase.execute(
      AuditAction.CREATE,
      AuditedEntity.DONOR,
      savedDonor.getId(),
      null,
      this.auditStateOf(savedDonor)
    );

    return savedDonor;
  }

  private Map<String, Object> auditStateOf(Donor donor) {
    var state = new LinkedHashMap<String, Object>();
    state.put("userId", donor.getUser().getId());
    state.put("name", donor.getUser().getName());
    state.put("email", donor.getUser().getEmail());
    state.put("userType", donor.getUser().getUserType());
    state.put("active", donor.getUser().isActive());
    state.put("document", donor.getDocument());
    state.put("reasonSocial", donor.getReasonSocial());

    return state;
  }
}
