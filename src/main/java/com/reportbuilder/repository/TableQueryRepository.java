package com.reportbuilder.repository;

import com.reportbuilder.model.TableQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableQueryRepository {
    void save(TableQuery tableQuery);

    void update(TableQuery tableQuery);

    void deleteById(int id);

    void executeById(int id);

    List<TableQuery> getAllByName(String name);

    Optional<TableQuery> getById(int id);

    List<TableQuery> getAll();
}
