package com.reportbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    //length 50;
    private String tableName;

    private int columnsAmount;

    private List<Column> columnInfos;

    private String primaryKey;
}
