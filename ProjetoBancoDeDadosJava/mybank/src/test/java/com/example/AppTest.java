package com.example;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class AppTest {
    @Test
    public void removesExistingDuplicatesAndPreventsNewOnes() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE clientes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nome TEXT NOT NULL, "
                    + "saldo REAL NOT NULL DEFAULT 0)");
            statement.executeUpdate("INSERT INTO clientes (id, nome, saldo) VALUES "
                    + "(2, 'Ana Souza', 2500.00), (8, 'Ana Souza', 2500.00), "
                    + "(10, 'Bruno Costa', 1800.50)");

            App.criarTabelaClientes(connection);
            App.inserirCliente(connection, "Ana Souza", 2500.00);
            App.inserirCliente(connection, "Ana Souza", 3000.00);

            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT MIN(id), COUNT(*) FROM clientes WHERE nome = 'Ana Souza'")) {
                resultSet.next();
                assertEquals(2, resultSet.getInt(1));
                assertEquals(1, resultSet.getInt(2));
            }

            try (ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM clientes")) {
                resultSet.next();
                assertEquals(2, resultSet.getInt(1));
            }
        }
    }
}
