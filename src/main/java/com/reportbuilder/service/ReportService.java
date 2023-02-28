package com.reportbuilder.service;

import com.reportbuilder.dto.ReportDto;
import com.reportbuilder.model.Column;
import com.reportbuilder.model.Report;
import com.reportbuilder.model.TableReport;
import com.reportbuilder.model.TypeOfData;
import com.reportbuilder.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    private void save(Report report) {
        if (report.getTableAmount() != report.getTables().size()) {
            throw new NoSuchElementException();
        }
        List<String> tableNames = new ArrayList<>();
        for (TableReport tableReport : report.getTables()) {
            String tableName = tableReport.getTableName();
            equalsTableColumns(reportRepository.getTableColumns(tableName), tableReport.getColumns());
            tableNames.add(tableName);
        }
        reportRepository.save(new ReportDto(report.getReportId(), tableNames));
    }

    private void equalsTableColumns(List<Column> baseColumns, List<Column> reportColumns) {
        for (int i = 0; i < baseColumns.size(); i++) {
            if (!baseColumns.get(i).getTitle().equalsIgnoreCase(reportColumns.get(i).getTitle())) {
                throw new NoSuchElementException();
            }
            if (!baseColumns.get(i).getType().equals(TypeOfData.INTEGER.toString())
                    && !reportColumns.get(i).getType().equals(TypeOfData.int4.toString())) {
                if (!baseColumns.get(i).getType().equals(TypeOfData.VARCHAR.toString())
                        && !baseColumns.get(i).getType().equals(TypeOfData.getCharacterVarying())) {
                    throw new NoSuchElementException();
                }
            }
        }
    }
}
