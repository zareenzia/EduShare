package com.edushare.file_sharing_app_backend.exception;

import com.edushare.file_sharing_app_backend.dto.ApiResponse;
import com.edushare.file_sharing_app_backend.dto.ErrorDetail;
import com.edushare.file_sharing_app_backend.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("Illegal argument exception occurred: ", ex);

        ApiResponse<Object> errorResponse = ApiResponse.error(
                ex.getMessage(),
                null,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        log.error("Resource not found: ", ex);
        return ResponseUtil.errorResponse(
                ex.getMessage(), HttpStatus.NOT_FOUND, null, request.getRequestURI());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthException(
            AuthException ex, HttpServletRequest request) {
        log.error("Authentication exception: ", ex);
        return ResponseUtil.errorResponse(
                ex.getMessage(), HttpStatus.UNAUTHORIZED, null, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Validation error: ", ex);
        return ResponseUtil.errorResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> ErrorDetail.builder()
                                .field(error.getField())
                                .message(error.getDefaultMessage())
                                .rejectedValue(error.getRejectedValue())
                                .code(error.getCode())
                                .build())
                        .toList(),
                request.getRequestURI());
    }

}
