package com.reportbuilder.repository;

import com.reportbuilder.model.SingleQuery;

import java.util.List;
import java.util.Optional;

public interface SingleQueryRepository {

    void save(SingleQuery singleQuery);

    void update(SingleQuery singleQuery);

    void deleteById(int id);

    void executeById(int id);

    Optional<SingleQuery> getById(int id);

    List<SingleQuery> getAll();
}
