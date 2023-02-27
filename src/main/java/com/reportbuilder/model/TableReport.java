package com.reportbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableReport {

    private String tableName;

    private List<ColumnReport> columns;
}
