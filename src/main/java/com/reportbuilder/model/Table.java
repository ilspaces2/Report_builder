package com.reportbuilder.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    //length 50;
    private String tableName;

    private int columnsAmount;

    private List<Column> columnInfos;

    private String primaryKey;
}
