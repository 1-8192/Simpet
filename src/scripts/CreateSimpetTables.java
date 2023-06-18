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
                                "    appuser_id serial NOT NULL PRIMARY KEY," +
                                "    username text NOT NULL" +
                                ")";
                statement1.executeUpdate(sql);
                sql = "CREATE TABLE IF NOT EXISTS Pet (" +
                        "pet_id serial NOT NULL PRIMARY KEY," +
                        "pet_name text NOT NULL," +
                        "mood int NOT NULL ," +
                        "health int NOT NULL," +
                        "has_passed boolean NOT NULL," +
                        "pet_type text NOT NULL," +
                        "breed text," +
                        "appuser_id integer REFERENCES Appuser (appuser_id)" +
                        ")";
                statement2.executeUpdate(sql);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
