package ru.itis.services;

import ru.itis.dto.AjaxDto;
import ru.itis.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UsersService {
    List<User> getAll() throws SQLException;

    void addUser(AjaxDto form) throws SQLException;

    void addUsers(List<User> users) throws SQLException;

    int getUsersCount() throws SQLException;

    List<User> findByCity(String city) throws SQLException;

    List<User> findByAgeGreaterThan(int age) throws SQLException;

    List<User> findByPhone(String phone) throws SQLException;
}
