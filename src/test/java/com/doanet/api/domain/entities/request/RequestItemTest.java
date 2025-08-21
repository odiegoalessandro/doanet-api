package com.doanet.api.domain.entities.request;

import com.doanet.api.domain.entities.item.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestItemTest {
  private Item createValidItem(){
    return new Item(1L, "Item Test", "Description", false, null);
  }

  private RequestItem createValidRequestItem() {
    return new RequestItem(1L, null, createValidItem(), 10);
  }

  @Test
  void shouldThrowException_WhenItemIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new RequestItem(1L, null, null, 10);
    });

    assertEquals("Item não pode ser nulo", exception.getMessage());
  }

  @Test
  void shouldThrowException_WhenQuantityIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new RequestItem(1L, null, createValidItem(), null);
    });

    assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
  }

  @Test
  void shouldThrowException_WhenQuantityIsZero() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new RequestItem(1L, null, createValidItem(), 0);
    });

    assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
  }

  @Test
  void shouldSettingsBeValid_WhenCreatingRequestItemWithValidParameters() {
    RequestItem requestItem = createValidRequestItem();

    assertNotNull(requestItem);
    assertEquals(1L, requestItem.getId());
    assertNotNull(requestItem.getItem());
    assertEquals(10, requestItem.getQuantity());
  }

  @Test
  void shouldThrowException_WhenSettingItemToNull() {
    RequestItem requestItem = createValidRequestItem();

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      requestItem.setItem(null);
    });

    assertEquals("Item não pode ser nulo", exception.getMessage());
  }

  void shouldThrowException_WhenSettingQuantityToNull() {
    RequestItem requestItem = createValidRequestItem();

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      requestItem.setQuantity(null);
    });

    assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
  }

  void shouldThrowException_WhenSettingQuantityToZero() {
    RequestItem requestItem = createValidRequestItem();

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      requestItem.setQuantity(0);
    });

    assertEquals("Quantidade deve ser maior que zero", exception.getMessage());
  }
}
