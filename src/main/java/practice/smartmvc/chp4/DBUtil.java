package practice.smartmvc.chp4;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:33066/product";
    private static final String userName = "root";
    private static final String password = "root";
    private static ThreadLocal<Connection> connectionContainer = new ThreadLocal<>();
    public static Connection getConnection() {
        Connection connection = connectionContainer.get();
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection() {
        Connection connection = connectionContainer.get();
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
