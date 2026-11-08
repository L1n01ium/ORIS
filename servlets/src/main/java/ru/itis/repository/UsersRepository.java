package ru.itis.repository;

import ru.itis.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UsersRepository extends CrudRepository<User> {
    List<User> findAll();

    int getUsersCount() throws SQLException;

    List<User> findByCity(String city) throws SQLException;

    List<User> findByAgeGreaterThan(int age) throws SQLException;

    List<User> findByPhone(String phone) throws SQLException;
}
