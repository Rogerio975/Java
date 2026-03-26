package com.model;

public class UmaClasse {
    private String nome;
    private int idade;

    public UmaClasse(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    public static void main(String[] args) {
        UmaClasse obj = new UmaClasse("João", 30);
        System.out.println("Nome: " + obj.getNome());
        System.out.println("Idade: " + obj.getIdade());
    }

}
