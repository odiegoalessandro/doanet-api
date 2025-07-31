package com.doanet.api.domain.entities.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ItemTest {
  @Test
  public void shouldThrowException_WhenNameIsNull(){
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Item(
        null,
        null,
        "arroz",
        false,
        null
      );
    });
  }

  @Test
  public void shouldThrowException_WhenNameIsBlank() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Item(null, "", "5kg", false, null);
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Item(null, "       ", "5kg", false, null);
    });
  }


  @Test
  public void shouldCreateItem_WhenNameIsValid(){
    Assertions.assertDoesNotThrow(() -> {
      new Item(
        null,
        "arroz",
        "5kg",
        false,
        null
      );
    });
  }

  @Test
  public void shouldThrowException_WhenIsPerishableIsTrueAndExpirationDateIsNull(){
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new Item(
        null,
        "arroz",
        "5kg",
        true,
        null
      );
    });
  }
}
