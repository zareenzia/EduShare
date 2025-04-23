package com.edushare.file_sharing_app_backend.util;

public class FileUtil {

    public FileUtil() {
    }

    public static String getFileContentType(String fileName) {
        String contentType;
        if (fileName.endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (fileName.endsWith(".png")) {
            contentType = "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else contentType = "application/octet-stream";
        return contentType;
    }
}
