package com.doanet.api.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
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

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
    String message = ex.getConstraintViolations().stream()
      .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
      .collect(Collectors.joining(", "));
    return buildError(HttpStatus.BAD_REQUEST, message, request);
  }

  @ExceptionHandler(TransactionSystemException.class)
  public ResponseEntity<ApiError> handleTransactionException(TransactionSystemException ex, HttpServletRequest request) {
    Throwable cause = ex.getRootCause();
    if (cause instanceof jakarta.validation.ConstraintViolationException violation) {
      String message = violation.getConstraintViolations().stream()
        .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
        .collect(Collectors.joining(", "));
      return buildError(HttpStatus.BAD_REQUEST, message, request);
    }

    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno na transação", request);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request){
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro genérico interno. " + ex.getMessage() + ex.getCause(), request);
  }

  private ResponseEntity<ApiError> buildError(HttpStatus status, String message, HttpServletRequest request) {
    ApiError error = new ApiError(status, message, request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }
}
