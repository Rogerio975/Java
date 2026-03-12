package com.example;

public class Produto {
    private Integer id;
    private String marca;
    private String nome;
    private double preco;

    // Construtor vazio e com campos
    public Produto() {}
    public Produto(String marca, String nome, double preco) {
        this.marca = marca;
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e Setters (Essenciais para o DAO)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}