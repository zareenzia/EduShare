package com.edushare.file_sharing_app_backend.constant;

public final class ErrorMessage {
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String USERNAME_NOT_FOUND = "Username not found";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String FILE_UPLOAD_FAILED = "File upload failed";
    public static final String FILE_DOWNLOAD_FAILED = "File download failed";
    public static final String INVALID_FILE_TYPE = "Invalid file type";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String USERNAME_PASSWORD_CAN_NOT_BE_NULL = "Username and password must not be null";
    public static final String ACCOUNT_SUSPENDED = "Account is suspended.";
    public static final String USER_ALREADY_IN_USE = "User is already in use.";
    public static final String EMAIL_ALREADY_IN_USE = "Email is already in use.";

    private ErrorMessage() {
        // Prevent instantiation
    }
}
