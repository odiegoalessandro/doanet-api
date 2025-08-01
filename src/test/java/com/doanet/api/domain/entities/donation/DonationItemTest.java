package com.doanet.api.domain.entities.donation;

import static org.junit.jupiter.api.Assertions.*;

import com.doanet.api.domain.entities.item.Item;
import org.junit.jupiter.api.Test;

class DonationItemTest {

  private Item validItem() {
    return new Item(1L, "Arroz", "Arroz branco 1kg", false, null);
  }

  @Test
  void shouldConstructWithValidData() {
    var item = new DonationItem(1L, 5, validItem(), null);
    assertEquals(5, item.getQuantity());
    assertEquals(1L, item.getItem().getId());
  }

  @Test
  void shouldThrow_WhenQuantityIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new DonationItem(1L, null, validItem(), null));
  }

  @Test
  void shouldThrow_WhenQuantityIsZero() {
    assertThrows(IllegalArgumentException.class, () -> new DonationItem(1L, 0, validItem(), null));
  }

  @Test
  void shouldThrow_WhenQuantityIsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new DonationItem(1L, -3, validItem(), null));
  }

  @Test
  void shouldThrow_WhenItemIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new DonationItem(1L, 5, null, null));
  }

  @Test
  void shouldThrow_WhenItemIdIsNull() {
    var invalidItem = new Item(null, "Feijão", "Feijão carioca", false, null);
    assertThrows(IllegalArgumentException.class, () -> new DonationItem(1L, 5, invalidItem, null));
  }

  @Test
  void shouldSetValidQuantity() {
    var item = new DonationItem(1L, 2, validItem(), null);
    item.setQuantity(10);
    assertEquals(10, item.getQuantity());
  }

  @Test
  void shouldThrow_WhenSettingInvalidQuantity() {
    var item = new DonationItem(1L, 2, validItem(), null);
    assertThrows(IllegalArgumentException.class, () -> item.setQuantity(0));
  }

  @Test
  void shouldSetValidItem() {
    var item = new DonationItem(1L, 2, validItem(), null);
    var newItem = new Item(2L, "Macarrão", "Macarrão integral", false, null);
    item.setItem(newItem);
    assertEquals(2L, item.getItem().getId());
  }

  @Test
  void shouldThrow_WhenSettingInvalidItem() {
    var item = new DonationItem(1L, 2, validItem(), null);
    assertThrows(IllegalArgumentException.class, () -> item.setItem(null));
  }

  @Test
  void shouldThrow_WhenSettingItemWithNullId() {
    var item = new DonationItem(1L, 2, validItem(), null);
    var invalidItem = new Item(null, "Leite", "Leite em pó", false, null);
    assertThrows(IllegalArgumentException.class, () -> item.setItem(invalidItem));
  }
}
