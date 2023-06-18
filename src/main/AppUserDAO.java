package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppUserDAO {
    public static void saveUserInfo (User currentUser) {
        String connectionUrl =
                "jdbc:postgresql://localhost/Simpet?"
                        + "user=alessandroallegranzi&"
                        + "password=Gwyn1/8192";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement1 = connection.createStatement();
             Statement statement2 = connection.createStatement();) {
            String sql =
                    "CREATE TABLE IF NOT EXISTS appuser" +
                            "(" +
                            "    id SERIAL PRIMARY KEY," +
                            "    username TEXT" +
                            ")";
            statement1.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
