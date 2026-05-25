package com.empresa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    // Credenciais de acesso ao servidor PostgreSQL
    static final String URL_PADRAO = "jdbc:postgresql://localhost:5432/postgres";
    static final String URL_EMPRESA = "jdbc:postgresql://localhost:5432/empresa";
    static final String USUARIO = "postgres";
    static final String SENHA = "1234"; // Altere para a sua senha

    public static void main(String[] args) {
        criarBancoDados();
        criarTabela();
        inserirDados();
        exibirTabela();
    }

    public static void criarBancoDados() {
        // Conecta no banco padrão apenas para disparar o comando de criar o novo banco
        try (Connection conn = DriverManager.getConnection(URL_PADRAO, USUARIO, SENHA);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE empresa";
            stmt.executeUpdate(sql);
            System.out.println("Banco de dados 'empresa' criado com sucesso!");

        } catch (SQLException e) {
            // O código de erro 42P04 no PostgreSQL significa que o banco já existe
            if ("42P04".equals(e.getSQLState())) {
                System.out.println("O banco de dados 'empresa' já existe.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void criarTabela() {
        // Agora conecta especificamente no banco 'empresa' recém-criado
        try (Connection conn = DriverManager.getConnection(URL_EMPRESA, USUARIO, SENHA);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS funcionarios (" +
                         "id SERIAL PRIMARY KEY, " +
                         "nome VARCHAR(100) NOT NULL, " +
                         "cpf VARCHAR(14) UNIQUE NOT NULL, " +
                         "endereco VARCHAR(255)" +
                         ")";

            stmt.executeUpdate(sql);
            System.out.println("Tabela 'funcionarios' configurada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirDados() {
        try (Connection conn = DriverManager.getConnection(URL_EMPRESA, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO funcionarios (nome, cpf, endereco) VALUES (?, ?, ?)")) {

            // Inserir dados de exemplo
            String[][] dados = {
                {"João Silva", "123.456.789-00", "Rua A, 100"},
                {"Maria Santos", "234.567.890-11", "Rua B, 200"},
                {"Pedro Oliveira", "345.678.901-22", "Rua C, 300"},
                {"Ana Costa", "456.789.012-33", "Rua D, 400"},
                {"Carlos Souza", "567.890.123-44", "Rua E, 500"}
            };

            for (String[] funcionario : dados) {
                stmt.setString(1, funcionario[0]);
                stmt.setString(2, funcionario[1]);
                stmt.setString(3, funcionario[2]);
                stmt.executeUpdate();
            }

            System.out.println("✓ " + dados.length + " funcionários inseridos com sucesso!");

        } catch (SQLException e) {
            // Ignorar erro se dados já existem (constraint unique no CPF)
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                System.out.println("ℹ Dados já existem na tabela.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void exibirTabela() {
        try (Connection conn = DriverManager.getConnection(URL_EMPRESA, USUARIO, SENHA);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM funcionarios";
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n========== TABELA FUNCIONARIOS ==========");
            
            if (!rs.isBeforeFirst()) {
                System.out.println("Tabela vazia!");
            } else {
                System.out.printf("%-5s | %-30s | %-14s | %-30s%n", "ID", "NOME", "CPF", "ENDERECO");
                System.out.println("-----+--------------------------------+----------------+--------------------------------");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String endereco = rs.getString("endereco");
                    System.out.printf("%-5d | %-30s | %-14s | %-30s%n", id, nome, cpf, endereco != null ? endereco : "");
                }
            }
            System.out.println("=========================================\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}