package com.reportbuilder.model;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    //length 50;
    private String tableName;

    private int columnsAmount;

    private List<Column> columnInfos;

    private String primaryKey;
}
