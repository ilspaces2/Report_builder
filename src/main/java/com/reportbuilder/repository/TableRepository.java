package com.reportbuilder.repository;

import com.reportbuilder.model.Table;

import java.util.Optional;

public interface TableRepository {

    void save(Table table);

    Optional<Table> getByName(String name);

    void deleteByName(String name);
}
