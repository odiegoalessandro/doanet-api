package com.doanet.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
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

}

