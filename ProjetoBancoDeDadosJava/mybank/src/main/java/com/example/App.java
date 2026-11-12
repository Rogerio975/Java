package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    private static final String DATABASE_URL = "jdbc:sqlite:mybank.db";

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
                criarTabelaClientes(connection);
                inserirCliente(connection, "Ana Souza", 2500.00);
                inserirCliente(connection, "Bruno Costa", 1800.50);
                listarClientes(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Erro ao processar o banco de dados do MyBank.", e);
        }
    }

    static void criarTabelaClientes(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nome TEXT NOT NULL, "
                + "saldo REAL NOT NULL DEFAULT 0)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            statement.executeUpdate("DELETE FROM clientes WHERE id NOT IN ("
                    + "SELECT MIN(id) FROM clientes GROUP BY nome, saldo)");
            statement.executeUpdate("CREATE UNIQUE INDEX IF NOT EXISTS idx_clientes_nome "
                    + "ON clientes (nome)");
        }
    }

    static void inserirCliente(Connection connection, String nome, double saldo) throws SQLException {
        String sql = "INSERT OR IGNORE INTO clientes (nome, saldo) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setDouble(2, saldo);
            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Cliente ja cadastrado: " + nome);
            }
        }
    }

    private static void listarClientes(Connection connection) throws SQLException {
        String sql = "SELECT id, nome, saldo FROM clientes ORDER BY id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Clientes cadastrados:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                double saldo = resultSet.getDouble("saldo");
                System.out.printf("ID %d | %s | Saldo: %.2f%n", id, nome, saldo);
            }
        }
    }
}
