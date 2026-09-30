package oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "1324";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_11-504";

    public static void main(String[] args) throws Exception {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            UserRepository userRepository = new UserRepositoryJdbcImpl(connection);
            List<User> usersToSave = Arrays.asList(
                    new User(null, "Pavel", "Kojevnikov", 19),
                    new User(null, "Nikolai", "Kozlov", 22),
                    new User(null, "Zaur", "Najafov", 19),
                    new User(null, "Nikita", "Alekseev", 19),
                    new User(null, "Aleksandra", "Pronkina", 19),
                    new User(null, "Darya", "Georgitsa", 20)
            );

            userRepository.saveAll(usersToSave);

            System.out.println("Пользователи с возрастом 19:");
            List<User> usersByAge = userRepository.findAllByAge(19);

            usersByAge.forEach(user -> System.out.println(user.getName() + " " + user.getSurname() + ", age=" + user.getAge()));
        }
    }
}