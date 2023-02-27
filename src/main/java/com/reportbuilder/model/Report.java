package com.reportbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private int reportId;

    private int tableAmount;

    private List<TableReport> tables;
}
