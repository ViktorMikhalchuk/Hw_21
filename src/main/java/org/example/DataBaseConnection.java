package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public static Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("MYSQL_URL");
        String dbUser = System.getenv("MYSQL_USER");
        String dbPassword = System.getenv("MYSQL_PASSWORD");
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
//Реалізувати клас DataBaseConnection.
//налаштувати підключення до БД
//метод getConnection() - повертає нове з'єднання з БД
//метод close(Connection) - закриває передане з'єднання
