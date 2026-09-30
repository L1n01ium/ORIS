package oop;

import java.sql.*;
import java.util.*;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;

    private static final String SQL_SELECT_FROM_DRIVERS = "select id, first_name, last_name, age from drivers";
    private static final String SQL_SELECT_BY_ID = "select id, first_name, last_name, age from drivers where id = ";
    private static final String SQL_SELECT_BY_AGE = "select id, first_name, last_name, age from drivers where age = ";
    private static final String SQL_INSERT_INTO_DRIVERS = "insert into drivers (first_name, last_name, age) values ";
    private static final String SQL_UPDATE_DRIVERS = "update drivers set first_name = '%s', last_name = '%s', age = %d where id = %d";
    private static final String SQL_DELETE_BY_ID = "delete from drivers where id = ";
    public UserRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVERS)) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        }

        return result;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_BY_ID + id)) {
            if (resultSet.next()) {
                return Optional.of(mapRow(resultSet));
            }

            return Optional.empty();
        }
    }

    @Override
    public void save(User entity) throws SQLException {
        saveAll(Collections.singletonList(entity));
    }

    @Override
    public void saveAll(List<User> users) throws SQLException {
        if (users == null || users.isEmpty()) {
            return;
        }

        StringBuilder sql = new StringBuilder(SQL_INSERT_INTO_DRIVERS);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            if (i > 0) {
                sql.append(", ");
            }

            sql.append("(")
                    .append("'").append(escape(user.getName())).append("'")
                    .append(", ")
                    .append("'").append(escape(user.getSurname())).append("'")
                    .append(", ")
                    .append(user.getAge())
                    .append(")");
        }

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        String sql = String.format(
                SQL_UPDATE_DRIVERS,
                escape(entity.getName()),
                escape(entity.getSurname()),
                entity.getAge(),
                entity.getId()
        );

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    @Override
    public void remove(User entity) throws SQLException {
        if (entity.getId() != null) {
            removeById(entity.getId());
        }
    }

    @Override
    public void removeById(Long id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_DELETE_BY_ID + id);
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        List<User> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_BY_AGE + age)) {
            while (resultSet.next()) {
                result.add(mapRow(resultSet));
            }
        }
        return result;
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("age")
        );
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("'", "''");
    }
}