
package qlvpp.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnections {

    private static final String URL = "jdbc:sqlserver://LAPTOP-IU1UF6TJ\\MSSQLSV:1433;databaseName=QuanLyVanPhongPham;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";

    // Hàm static trả về Connection để tái sử dụng
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // có thể thay bằng JOptionPane.showMessageDialog nếu muốn thông báo GUI
        }
        return conn;
    }
}
