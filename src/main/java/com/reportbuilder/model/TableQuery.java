package com.reportbuilder.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableQuery {

    @Min(1)
    private int queryId;

    @NotBlank
    @Size(max = 50)
    private String tableName;

    @NotBlank
    @Size(max = 120)
    private String query;
}
