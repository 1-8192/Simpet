package scripts;

import java.sql.*;

/**
 * Scripts to create the tables needed for the Simpet simulation. Please replace the postgres address and credentials
 * with you own. If you have a copy of the DB already, no need to run this.
 */
public class CreateSimpetTables {
        public static void main(String[] args) {
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
                                "    appuser_id SERIAL PRIMARY KEY," +
                                "    username TEXT" +
                                ")";
                statement1.executeUpdate(sql);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
