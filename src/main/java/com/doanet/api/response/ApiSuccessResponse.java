package com.doanet.api.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
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
}
