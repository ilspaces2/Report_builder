package com.reportbuilder.service;

import com.reportbuilder.exception.TableException;
import com.reportbuilder.model.Column;
import com.reportbuilder.model.Table;
import com.reportbuilder.model.TypeOfData;
import com.reportbuilder.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public void save(Table table) {
        if (tableExists(table.getTableName())) {
            throw new TableException("Table already exists");
        }
        tableRepository.save(parseFromTableToSql(table));
    }

    public Table getByName(String name) {
        return tableRepository.getByName(name).orElse(null);
    }

    public void deleteByName(String name) {
        if (!tableExists(name)) {
            throw new TableException("Table not found");
        }
        tableRepository.deleteByName(name);
    }

    private String parseFromTableToSql(Table table) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(table.getTableName()).append(" (");
        boolean definedPrimaryKey = false;
        for (int i = 0; i < table.getColumnsAmount(); i++) {
            Column column = table.getColumnInfos().get(i);
            String title = column.getTitle();
            String type = column.getType();
            validateColumnTitleAndType(title, type);
            if (!definedPrimaryKey && table.getPrimaryKey().equals(title)) {
                sb.append(column.getTitle()).append(" ").append(column.getType());
                sb.append(" primary key");
                definedPrimaryKey = true;
            } else {
                sb.append(column.getTitle()).append(" ").append(column.getType());
            }
            if (i < table.getColumnsAmount() - 1) {
                sb.append(", ");
            }
        }
        if (!definedPrimaryKey) {
            throw new TableException("Primary key not defined");
        }
        return sb.append(");").toString();
    }

    private void validateColumnTitleAndType(String title, String type) {
        if (title.matches(".*[а-яА-Я].*")) {
            throw new TableException("Title should only be in english: " + title);
        }
        if (!type.matches(TypeOfData.VARCHAR + "\\([\\d]*\\)" +
                "|" + TypeOfData.VARCHAR +
                "|" + TypeOfData.varchar +
                "|" + TypeOfData.int4)) {
            throw new TableException("Type incorrect: " + type);
        }
    }

    private boolean tableExists(String tableName) {
        return tableRepository.tableExists(tableName);
    }
}

