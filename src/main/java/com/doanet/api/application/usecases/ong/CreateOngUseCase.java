package com.doanet.api.application.usecases.ong;

import com.doanet.api.application.commands.CreateOngCommand;
import com.doanet.api.application.gateways.OngRepository;
import com.doanet.api.application.gateways.PasswordHasher;
import com.doanet.api.application.usecases.audit.RecordAuditUseCase;
import com.doanet.api.application.usecases.user.GeolocateUserUseCase;
import com.doanet.api.domain.entities.ong.Ong;
import com.doanet.api.domain.entities.user.User;
import com.doanet.api.domain.enums.AuditAction;
import com.doanet.api.domain.enums.AuditedEntity;
import com.doanet.api.domain.enums.UserType;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateOngUseCase {
  private final OngRepository ongRepository;
  private final GeolocateUserUseCase geolocateUserUseCase;
  private final PasswordHasher passwordHasher;
  private final RecordAuditUseCase recordAuditUseCase;

  public CreateOngUseCase(
      OngRepository ongRepository,
      GeolocateUserUseCase geolocateUserUseCase,
      PasswordHasher passwordHasher,
      RecordAuditUseCase recordAuditUseCase) {
    this.ongRepository = ongRepository;
    this.geolocateUserUseCase = geolocateUserUseCase;
    this.passwordHasher = passwordHasher;
    this.recordAuditUseCase = recordAuditUseCase;
  }

  public Ong execute(CreateOngCommand ongCommand) {
    var user =
        new User(
            null,
            ongCommand.name(),
            ongCommand.email(),
            ongCommand.password(),
            ongCommand.phone(),
            ongCommand.street(),
            ongCommand.number(),
            ongCommand.neighborhood(),
            ongCommand.city(),
            ongCommand.state(),
            ongCommand.zipCode(),
            null,
            null,
            UserType.ONG,
            true);

    user.setPassword(this.passwordHasher.hash(ongCommand.password()));

    this.geolocateUserUseCase.execute(user);

    Ong ong = new Ong(null, user, ongCommand.cnpj());

    var savedOng = this.ongRepository.save(ong);

    this.recordAuditUseCase.execute(
        AuditAction.CREATE, AuditedEntity.ONG, savedOng.getId(), null, this.auditStateOf(savedOng));

    return savedOng;
  }

  private Map<String, Object> auditStateOf(Ong ong) {
    var state = new LinkedHashMap<String, Object>();
    state.put("userId", ong.getUser().getId());
    state.put("name", ong.getUser().getName());
    state.put("email", ong.getUser().getEmail());
    state.put("userType", ong.getUser().getUserType());
    state.put("active", ong.getUser().isActive());
    state.put("cnpj", ong.getCnpj());

    return state;
  }
}
