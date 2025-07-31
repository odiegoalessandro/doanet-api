package com.doanet.api.domain.entities.request;

import com.doanet.api.domain.entities.item.Item;

// TODO: Criar testes unitários para a classe RequestItem
public class RequestItem {
  private Long id;
  private Request request;
  private Item item;
  private Integer quantity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Request getRequest() {
    return request;
  }

  public void setRequest(Request request) {
    this.request = request;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    setItem(item);
    this.item = item;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  public RequestItem(Long id, Request request, Item item, Integer quantity) {
    validateItem(item);
    validateQuantity(quantity);

    this.id = id;
    this.request = request;
    this.item = item;
    this.quantity = quantity;
  }

  private void validateQuantity(Integer quantity) {
    if (quantity == null || quantity <= 0)
      throw new IllegalArgumentException("Quantidade deve ser maior que zero");
  }

  private void validateItem(Item item) {
    if (item == null || item.getId() == null)
      throw new IllegalArgumentException("Item inválido");
  }
}
