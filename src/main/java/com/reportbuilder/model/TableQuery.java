package com.reportbuilder.model;

import lombok.Data;

@Data
public class TableQuery {

    private int queryId;

    //length 50;
    private String tableName;

    //length 120;
    private String query;
}
