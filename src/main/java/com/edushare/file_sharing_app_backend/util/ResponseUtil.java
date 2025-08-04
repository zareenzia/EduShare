package com.edushare.file_sharing_app_backend.util;

import com.edushare.file_sharing_app_backend.dto.ApiResponse;
import com.edushare.file_sharing_app_backend.dto.ErrorDetail;
import com.edushare.file_sharing_app_backend.dto.PagedResponse;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import java.util.List;

@UtilityClass
public class ResponseUtil {

    // Success response methods
    public static <T> ResponseEntity<ApiResponse<T>> successResponse(T data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> successResponse(T data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }

    public static ResponseEntity<ApiResponse<Object>> successResponse(String message) {
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    public static <T> ResponseEntity<ApiResponse<Object>> createdResponse(T data, String message) {
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .statusCode(201)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Error response methods

    public static ResponseEntity<ApiResponse<Object>> errorResponse(
            String message,
            HttpStatus status,
            Object errors,
            String path) {
        ApiResponse<Object> response = ApiResponse.error(message, errors, path, status.value());
        return ResponseEntity.status(status).body(response);
    }

    // Validation error response
    public static ResponseEntity<ApiResponse<Object>> validationError(List<FieldError> fieldErrors) {
        List<ErrorDetail> errors = fieldErrors.stream()
                .map(error -> ErrorDetail.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .code(error.getCode())
                        .build())
                .toList();

        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Validation failed", errors, HttpStatus.BAD_REQUEST.value()));
    }

    // Paginated response
    public static <T> ResponseEntity<ApiResponse<PagedResponse<T>>> pagedSuccess(Page<T> page) {
        PagedResponse<T> pagedResponse = PagedResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();

        return ResponseEntity.ok(ApiResponse.success(pagedResponse, "Data retrieved successfully"));
    }
}
