package com.doanet.api.infra.interfaces;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
  private final int status;
  private final String error;
  private final String message;
  private final String path;
  private final LocalDateTime timestamp = LocalDateTime.now();

  public ApiError(HttpStatus status, String message, String path) {
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.message = message;
    this.path = path;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}


