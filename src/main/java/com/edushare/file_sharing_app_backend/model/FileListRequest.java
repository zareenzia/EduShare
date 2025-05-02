package com.edushare.file_sharing_app_backend.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class FileListRequest {
    @Length(max = 256, message = "Search text length must not exceed 256 characters")
    private String searchParam = "";

    @Pattern(regexp = "^(?:fileName|title|courseName|courseCode|department|instructor|tags|semester)?$", message = "Sorting parameters mismatched.")
    private String colName= "fileName";

    @Pattern(regexp = "^(?:ASC|DESC|NONE)?$", message = "Sorting direction mismatched.")
    private String sortDirection = "ASC";

    @Min(value = 0, message = "The Page size must be positive, starts from 0!")
    private int currentPageNumber = 0;

    @Min(value = 1, message = "The row limit must be positive and greater than 0!")
    private int rowsPerPage = 5;
}
