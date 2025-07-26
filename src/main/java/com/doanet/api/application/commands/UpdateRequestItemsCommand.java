package com.doanet.api.application.commands;

import java.util.List;

public record UpdateRequestItemsCommand(
  List<RequestItemCommand> items
) {
}
