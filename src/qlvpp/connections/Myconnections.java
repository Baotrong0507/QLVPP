package qlvpp.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnections {

    private static final String URL = "jdbc:mysql://localhost:3306/quanlyvanphongpham?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Sử dụng driver cũ cho mysql-connector-java-5.1.48
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return conn;
    }
}