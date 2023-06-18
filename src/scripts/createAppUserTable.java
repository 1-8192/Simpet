package scripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class createAppUserTable {
        public static void main(String[] args) {
            String connectionUrl =
                    "jdbc:postgresql://localhost/Simpet;"
                            + "database=Simpet;"
                            + "user=alessandroallegranzi;"
                            + "password=Gwyn1/8192;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true;"
                            + "loginTimeout=30;";
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement1 = connection.createStatement();
                 Statement statement2 = connection.createStatement();) {
                String sql =
                        "CREATE TABLE Food_type (" +
                                "food_type_id INTEGER NOT NULL PRIMARY KEY," +
                                "food_type VARCHAR(64) NOT NULL)";
                statement1.executeUpdate(sql);
                sql =
                        "CREATE TABLE Food (" +
                                "food_id INTEGER NOT NULL PRIMARY KEY," +
                                "name VARCHAR(64) NOT NULL," +
                                "date_available DATE NOT NULL," +
                                "food_type_id INTEGER NOT NULL REFERENCES Food_type(food_type_id))";
                statement2.executeUpdate(sql);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
