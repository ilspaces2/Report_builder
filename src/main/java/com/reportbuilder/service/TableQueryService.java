package com.reportbuilder.service;

import com.reportbuilder.exception.TableException;
import com.reportbuilder.model.TableQuery;
import com.reportbuilder.repository.TableQueryRepository;
import com.reportbuilder.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TableQueryService {

    private final TableQueryRepository tableQueryRepository;

    private final TableRepository tableRepository;


    //TODO: validate query?
    public void save(TableQuery tableQuery) {
        if (!tableRepository.tableExists(tableQuery.getTableName())) {
            throw new TableException("Table not found");
        }
        tableQueryRepository.save(tableQuery);
    }

    public void update(TableQuery tableQuery) {
        if (!tableRepository.tableExists(tableQuery.getTableName()) ||
                tableQueryRepository.getById(tableQuery.getQueryId()).isEmpty()) {
            throw new TableException("Table or queryId not found");
        }
        tableQueryRepository.update(tableQuery);
    }

    public void deleteById(int id) {
        if (tableQueryRepository.getById(id).isEmpty()) {
            throw new TableException("QueryId not found");
        }
        tableQueryRepository.deleteById(id);
    }

    public void execute(int id) {
        if (tableQueryRepository.getById(id).isEmpty()) {
            throw new TableException("QueryId not found");
        }
        tableQueryRepository.executeById(id);
    }

    public List<TableQuery> getAllByName(String name) {
        if (!tableRepository.tableExists(name)) {
            return null;
        }
        return tableQueryRepository.getAllByName(name);
    }

    //TODO check exception (need 500 Internal Server Error)
    public TableQuery getById(int id) {
        if (tableQueryRepository.getById(id).isEmpty()) {
            throw new NoSuchElementException();
        }
        return tableQueryRepository.getById(id).get();
    }

    public List<TableQuery> getAll() {
        return tableQueryRepository.getAll();
    }
}
