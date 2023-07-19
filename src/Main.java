import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!");
            throw new RuntimeException(e);
        }

        String url = "jdbc:postgresql://localhost:5432/matrix_151";
        String user = "postgres";
        String password = "";

        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connection is successfully!");
            Statement statement = connection.createStatement();
//            statement.execute("create table customer\n" +
//                    "(\n" +
//                    "    id   serial\n" +
//                    "        constraint customer_pk\n" +
//                    "            primary key,\n" +
//                    "    name varchar,\n" +
//                    "    age  integer\n" +
//                    ");");
            String name = scanner.next();
            int age = scanner.nextInt();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into customer (name, age) values (?, ?)"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
//            statement.execute("insert into customer (name, age) values ('Elnur', 27)");
//            statement.execute("insert into customer (name, age) values ('Ali', 22)");
//            statement.execute("insert into customer (name, age) values ('Elizamin', 21)");

            ResultSet resultSet = statement.executeQuery("select * from customer");

            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(
                        new Customer(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("age")
                        )
                );
            }

//            statement.execute("truncate customer");

            System.out.println(customers);
        }

    }
}