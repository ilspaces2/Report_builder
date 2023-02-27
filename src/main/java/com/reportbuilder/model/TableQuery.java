package com.reportbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableQuery {

    private int queryId;

    //length 50;
    private String tableName;

    //length 120;
    private String query;
}
