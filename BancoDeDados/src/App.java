import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5432;
        String user = "postgres";
        String password = "postgres";
        String dbName = "Supermercado";

        if (args.length >= 5) {
            host = args[0];
            port = Integer.parseInt(args[1]);
            user = args[2];
            password = args[3];
            dbName = args[4];
        }

        try {
            Class.forName("org.postgresql.Driver");
            DBManager.createDatabase(host, port, user, password, dbName);
            DBManager.createTable(host, port, user, password, dbName);
            System.out.println("Banco e tabela prontos.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Falha ao criar banco/tabela: " + e.getMessage());
        }
    }
}
