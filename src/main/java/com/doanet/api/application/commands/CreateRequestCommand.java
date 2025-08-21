package com.doanet.api.application.commands;

import java.util.List;

public record CreateRequestCommand(
  Long donationPointId,
  Long ongId,
  List<RequestItemCommand> items
) {
}
