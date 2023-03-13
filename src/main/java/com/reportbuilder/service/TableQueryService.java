package com.reportbuilder.service;

import com.reportbuilder.exception.QueryException;
import com.reportbuilder.exception.TableException;
import com.reportbuilder.model.TableQuery;
import com.reportbuilder.repository.TableQueryRepository;
import com.reportbuilder.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableQueryService {

    private final TableQueryRepository tableQueryRepository;

    private final TableRepository tableRepository;


    //TODO: validate query?
    public void save(TableQuery tableQuery) {
        checkTable(tableQuery.getTableName());
        tableQueryRepository.save(tableQuery);
    }

    public void update(TableQuery tableQuery) {
        checkId(tableQuery.getQueryId());
        checkTable(tableQuery.getTableName());
        tableQueryRepository.update(tableQuery);
    }

    public void deleteById(int id) {
        checkId(id);
        tableQueryRepository.deleteById(id);
    }

    public void execute(int id) {
        checkId(id);
        tableQueryRepository.executeById(id);
    }

    public List<TableQuery> getAllByName(String name) {
        if (!tableRepository.tableExists(name)) {
            return null;
        }
        return tableQueryRepository.getAllByName(name);
    }

    public TableQuery getById(int id) {
        Optional<TableQuery> tableQuery = tableQueryRepository.getById(id);
        if (tableQuery.isEmpty()) {
            throw new NoSuchElementException("QueryId not found");
        }
        return tableQuery.get();
    }

    public List<TableQuery> getAll() {
        return tableQueryRepository.getAll();
    }

    private void checkId(int id) {
        if (tableQueryRepository.getById(id).isEmpty()) {
            throw new QueryException("QueryId not found");
        }
    }

    private void checkTable(String name) {
        if (!tableRepository.tableExists(name)) {
            throw new TableException("Table not found");
        }
    }
}
