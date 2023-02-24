package com.reportbuilder.repository;

import com.reportbuilder.model.SingleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SingleQueryRepository {

    void save(SingleQuery singleQuery);

    void update(SingleQuery singleQuery);

    void deleteById(int id);

    void executeById(int id);

    Optional<SingleQuery> getById(int id);

    List<SingleQuery> getAll();
}
