package com.reportbuilder.repository;

import com.reportbuilder.dto.ReportDto;
import com.reportbuilder.model.Column;
import com.reportbuilder.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {

    void save(ReportDto reportDto);

    Optional<Report> getById(int id);

    List<Column> getTableColumns(String tableName);
}
