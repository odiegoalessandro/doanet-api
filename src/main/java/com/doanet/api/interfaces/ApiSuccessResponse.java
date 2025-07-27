package com.doanet.api.interfaces;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiSuccessResponse<T> {
  private final HttpStatus status;
  private final String message;
  private final T data;
  private final LocalDateTime timestamp = LocalDateTime.now();

  public ApiSuccessResponse(HttpStatus status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }
}