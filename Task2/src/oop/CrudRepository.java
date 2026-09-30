package oop;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findAll() throws Exception;
    Optional<T> findById(Long id) throws Exception;
    void save(T entity) throws Exception;
    void update(T entity) throws Exception;
    void remove(T entity) throws Exception;
    void removeById(Long id) throws Exception;
}