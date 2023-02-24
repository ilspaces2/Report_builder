package com.reportbuilder.repository;

import com.reportbuilder.model.Table;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository {

    void save(Table table);

    Optional<Table> getByName(String name);

    void deleteByName(String name);
}
