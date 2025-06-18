package com.doanet.api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request){
    String message = ex.getBindingResult().getFieldErrors().stream()
      .map(f ->
        f.getField() + ": " + f.getDefaultMessage()
      ).collect(Collectors.joining(", "));

    return buildError(HttpStatus.BAD_REQUEST, message, request);
  }

  @ExceptionHandler({CoordinatesInternalServerException.class, HttpServerErrorException.InternalServerError.class})
  public ResponseEntity<ApiError> handleCoordinatesInternalError(RuntimeException ex, HttpServletRequest request){
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
  }

  @ExceptionHandler({CoordinatesNotFoundException.class, EntityNotFoundException.class})
  public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest request){
    return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
    return buildError(HttpStatus.NOT_FOUND, "Rota não encontrada", request);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request){
    return buildError(HttpStatus.NOT_FOUND, "Erro genérico interno", request);
  }

  private ResponseEntity<ApiError> buildError(HttpStatus status, String message, HttpServletRequest request) {
    ApiError error = new ApiError(status, message, request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }
}
