package com.empresa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class App {
    // Credenciais de acesso ao servidor PostgreSQL
    static final String URL_PADRAO = "jdbc:postgresql://localhost:5432/postgres";
    static final String URL_EMPRESA = "jdbc:postgresql://localhost:5432/empresa";
    static final String USUARIO = "postgres";
    static final String SENHA = "1234"; // Altere para a sua senha

    public static void main(String[] args) {
        criarBancoDados();
        criarTabela();
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