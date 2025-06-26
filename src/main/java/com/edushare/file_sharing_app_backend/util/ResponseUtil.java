package com.edushare.file_sharing_app_backend.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    private ResponseUtil() {
        // Private constructor to prevent instantiation
    }
    public static ResponseEntity<Object> successResponse(String message, Object data, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Object> successResponse(String message, Object data) {
        return successResponse(message, data, HttpStatus.OK);
    }

    public static ResponseEntity<Object> successResponse(String message) {
        return successResponse(message, null, HttpStatus.OK);
    }
}
