package com.doanet.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CoordinatesInternalServerException extends RuntimeException {
  public CoordinatesInternalServerException(String message) {
    super(message);
  }
}
