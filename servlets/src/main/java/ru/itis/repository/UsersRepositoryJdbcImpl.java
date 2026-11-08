package ru.itis.repository;

import ru.itis.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private static final String SQL_SELECT_ALL = "select * from account";
    private static final String SQL_INSERT = "insert into account(first_name, last_name, email, password, age, city, phone) " +
            "values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_CITY = "select * from account where city = ?";
    private static final String SQL_SELECT_BY_AGE = "select * from account where age > ? order by age";
    private static final String SQL_SELECT_BY_PHONE = "select * from account where phone = ?";
    private static final String SQL_SELECT_USERS_COUNT =
            "select value from app_settings where name = 'users_count'";

    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {

            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(userFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getUsersCount() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_USERS_COUNT)) {

            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("value"));
            }
            return 0;
        }
    }

    @Override
    public List<User> findByCity(String city) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_CITY)) {

            preparedStatement.setString(1, city);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(userFromResultSet(resultSet));
                }
                return result;
            }
        }
    }

    @Override
    public List<User> findByAgeGreaterThan(int age) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_AGE)) {

            preparedStatement.setInt(1, age);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(userFromResultSet(resultSet));
                }
                return result;
            }
        }
    }

    @Override
    public List<User> findByPhone(String phone) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_PHONE)) {

            preparedStatement.setString(1, phone);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(userFromResultSet(resultSet));
                }
                return result;
            }
        }
    }

    @Override
    public void save(User entity) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setInt(5, entity.getAge() == null ? 0 : entity.getAge());
            preparedStatement.setString(6, entity.getCity());
            preparedStatement.setString(7, entity.getPhone());
            preparedStatement.executeUpdate();
        }
    }

    private User userFromResultSet(ResultSet resultSet) throws SQLException {
        String ageAsString = resultSet.getString("age");
        Integer age = ageAsString == null ? null : Integer.parseInt(ageAsString);

        return User.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("first_name"))
                .surname(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .age(age)
                .city(resultSet.getString("city"))
                .phone(resultSet.getString("phone"))
                .build();
    }
}
