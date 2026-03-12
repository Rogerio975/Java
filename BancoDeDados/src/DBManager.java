import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    public static void createDatabase(String host, int port, String user, String password, String dbName) throws SQLException {
        String url = "jdbc:postgresql://" + host + ":" + port + "/postgres";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            String sql = "CREATE DATABASE \"" + dbName + "\"";
            st.executeUpdate(sql);
            System.out.println("Database created: " + dbName);
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().equals("42P04")) {
                System.out.println("Database already exists: " + dbName);
            } else {
                throw e;
            }
        }
    }

    public static void createTable(String host, int port, String user, String password, String dbName) throws SQLException {
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Produtos ("
                    + "Id SERIAL PRIMARY KEY,"
                    + "Marca VARCHAR(255),"
                    + "Nome_do_Produto VARCHAR(255),"
                    + "Preco NUMERIC(10,2)"
                    + ")";
            st.executeUpdate(sql);
            System.out.println("Table 'Produtos' ensured in database: " + dbName);
        }
    }
}
