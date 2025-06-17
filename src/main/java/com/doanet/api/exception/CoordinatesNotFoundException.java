package com.doanet.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CoordinatesNotFoundException extends RuntimeException {
  public CoordinatesNotFoundException(String message) {
    super(message);
  }
}
