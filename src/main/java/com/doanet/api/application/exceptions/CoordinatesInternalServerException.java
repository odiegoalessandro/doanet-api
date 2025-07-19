package com.doanet.api.application.exceptions;


public class CoordinatesInternalServerException extends RuntimeException {
  public CoordinatesInternalServerException(String message) {
    super(message);
  }

  public CoordinatesInternalServerException(String message, Throwable cause) {
    super(message, cause);
  }
}
