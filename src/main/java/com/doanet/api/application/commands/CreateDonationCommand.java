package com.doanet.api.application.commands;

import java.util.List;

public record CreateDonationCommand(
  Long donorId,
  Long donationPointId,
  List<CreateDonationItemCommand> items
) {}
