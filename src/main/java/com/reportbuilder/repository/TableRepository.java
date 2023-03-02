package com.reportbuilder.repository;

import com.reportbuilder.model.Table;

import java.util.Optional;

public interface TableRepository {

    void save(String sql);

    Optional<Table> getByName(String name);

    void deleteByName(String name);
}
