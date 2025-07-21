package com.doanet.api.domain.entities.ong;

import com.doanet.api.domain.entities.user.User;
import com.doanet.api.legacy.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OngTest {
  @Test
  public void shouldThrowException_WhenCnpjIsNull(){
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      var user = new User(null,
        "Nome",
        "email@valido.com",
        "senha123",
        "11999999999",
        "Rua",
        "123",
        "Bairro",
        "Cidade",
        "SP",
        "01234-567",
        -23.0,
        -46.0,
        UserType.ONG,
        true
      );

      new Ong(
        null,
        user,
        null
      );
    });
  }

  @Test
  public void shouldThrowException_WhenCnpjIsInvalid(){
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      var user = new User(null,
        "Nome",
        "email@valido.com",
        "senha123",
        "11999999999",
        "Rua",
        "123",
        "Bairro",
        "Cidade",
        "SP",
        "01234-567",
        -23.0,
        -46.0,
        UserType.ONG,
        true
      );

      new Ong(
        null,
        user,
        "          "
      );
    });
  }

}
