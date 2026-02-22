-- Script para criar o banco Supermercado e a tabela Produtos
CREATE DATABASE "Supermercado";
\connect "Supermercado";

CREATE TABLE IF NOT EXISTS Produtos (
    Id SERIAL PRIMARY KEY,
    Marca VARCHAR(255),
    Nome_do_Produto VARCHAR(255),
    Preco NUMERIC(10,2)
);
