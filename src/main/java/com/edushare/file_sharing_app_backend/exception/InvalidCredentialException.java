package com.edushare.file_sharing_app_backend.exception;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
