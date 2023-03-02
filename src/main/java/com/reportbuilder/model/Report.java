package com.reportbuilder.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Min(1)
    private int reportId;

    @Min(1)
    private int tableAmount;

    @NotNull
    private List<TableReport> tables;
}
