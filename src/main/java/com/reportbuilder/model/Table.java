package com.reportbuilder.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    @Size(min = 3, max = 50)
    @Pattern(regexp = "[^а-яА-Я]*", message = "should only be in english")
    private String tableName;

    @Min(1)
    private int columnsAmount;

    @NotNull(message = "primary key column is required")
    private List<Column> columnInfos;

    @NotBlank
    private String primaryKey;
}
