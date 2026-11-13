package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
    private static final String DATABASE_URL = "jdbc:sqlite:mybank.db";

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(DATABASE_URL);
                 Scanner scanner = new Scanner(System.in)) {
                criarTabelaClientes(connection);
                executarMenu(connection, scanner);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new IllegalStateException("Erro ao processar o banco de dados do MyBank.", e);
        }
    }

    private static void executarMenu(Connection connection, Scanner scanner) throws SQLException {
        while (true) {
            System.out.println("\n--- MyBank | Clientes ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Atualizar cliente");
            System.out.println("4. Excluir cliente");
            System.out.println("0. Sair");
            System.out.print("Opcao: ");

            if (!scanner.hasNextLine()) {
                return;
            }

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    String nome = lerNome(scanner);
                    Double saldo = lerSaldo(scanner);
                    if (nome == null || saldo == null) {
                        return;
                    }
                    inserirCliente(connection, nome, saldo);
                }
                case "2" -> listarClientes(connection);
                case "3" -> {
                    Integer id = lerId(scanner);
                    if (id == null) {
                        return;
                    }
                    String nome = lerNome(scanner);
                    Double saldo = lerSaldo(scanner);
                    if (nome == null || saldo == null) {
                        return;
                    }
                    if (atualizarCliente(connection, id, nome, saldo)) {
                        System.out.println("Cliente atualizado.");
                    } else {
                        System.out.println("Cliente nao encontrado.");
                    }
                }
                case "4" -> {
                    Integer id = lerId(scanner);
                    if (id == null) {
                        return;
                    }
                    if (excluirCliente(connection, id)) {
                        System.out.println("Cliente excluido.");
                    } else {
                        System.out.println("Cliente nao encontrado.");
                    }
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Opcao invalida.");
            }
        }
    }

    private static String lerNome(Scanner scanner) {
        while (true) {
            System.out.print("Nome: ");
            if (!scanner.hasNextLine()) {
                return null;
            }
            String nome = scanner.nextLine().trim();
            if (!nome.isEmpty()) {
                return nome;
            }
            System.out.println("O nome nao pode ficar vazio.");
        }
    }

    private static Integer lerId(Scanner scanner) {
        while (true) {
            System.out.print("ID do cliente: ");
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                int id = Integer.parseInt(scanner.nextLine().trim());
                if (id > 0) {
                    return id;
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Informe um ID inteiro positivo.");
                continue;
            }
            System.out.println("Informe um ID inteiro positivo.");
        }
    }

    private static Double lerSaldo(Scanner scanner) {
        while (true) {
            System.out.print("Saldo: ");
            if (!scanner.hasNextLine()) {
                return null;
            }
            try {
                double saldo = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(saldo)) {
                    return saldo;
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Informe um saldo numerico valido.");
                continue;
            }
            System.out.println("Informe um saldo numerico valido.");
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
            int sequenciasAtualizadas = statement.executeUpdate(
                    "UPDATE sqlite_sequence SET seq = "
                            + "(SELECT COALESCE(MAX(id), 0) FROM clientes) "
                            + "WHERE name = 'clientes'");
            if (sequenciasAtualizadas == 0) {
                statement.executeUpdate("INSERT INTO sqlite_sequence (name, seq) "
                        + "SELECT 'clientes', COALESCE(MAX(id), 0) FROM clientes");
            }
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
            } else {
                System.out.println("Cliente cadastrado.");
            }
        }
    }

    static boolean atualizarCliente(Connection connection, int id, String nome, double saldo)
            throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, saldo = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            statement.setDouble(2, saldo);
            statement.setInt(3, id);
            return statement.executeUpdate() > 0;
        }
    }

    static boolean excluirCliente(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private static void listarClientes(Connection connection) throws SQLException {
        String sql = "SELECT id, nome, saldo FROM clientes ORDER BY id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Clientes cadastrados:");
            boolean encontrouCliente = false;
            while (resultSet.next()) {
                encontrouCliente = true;
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                double saldo = resultSet.getDouble("saldo");
                System.out.printf("ID %d | %s | Saldo: %.2f%n", id, nome, saldo);
            }
            if (!encontrouCliente) {
                System.out.println("Nenhum cliente cadastrado.");
            }
        }
    }
}
