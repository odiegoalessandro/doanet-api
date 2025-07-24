package com.doanet.api.application.commands;

import java.util.List;

public record UpdateDonationItemsCommand(
  List<DonationItemCommand>items
) { }
