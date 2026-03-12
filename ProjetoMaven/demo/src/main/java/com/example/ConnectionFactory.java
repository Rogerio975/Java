package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/supermercado", "postgres", "1234");
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão", e);
        }
    }
}