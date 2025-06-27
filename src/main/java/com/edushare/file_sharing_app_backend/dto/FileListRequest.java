package com.edushare.file_sharing_app_backend.dto;

import lombok.Data;

@Data
public class FileListRequest {
    private String searchParam = "";

    private String colName= "fileName";

    private String sortDirection = "ASC";

    private int currentPageNumber = 0;

    private int rowsPerPage = 5;
}
