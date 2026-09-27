import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";

    private static final String DB_PASSWORD = "1324";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_11-504";


    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from drivers");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " " +
                    resultSet.getString("first_name"));
        }

        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        String lastName = scanner.nextLine();
        Integer age = scanner.nextInt();

        String sqlUnsertUser = "insert into drivers (first_name, last_name, age) " +
                "values (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlUnsertUser);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setInt(3, age);

        int affectedRows = preparedStatement.executeUpdate();

        System.out.println("Было добавлено " + affectedRows + " строк");
    }
}