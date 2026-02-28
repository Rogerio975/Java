package com.example;
public class Principal {
    public static void main(String[] args) {
        Produto novoProduto = new Produto("Coca-Cola", "Refrigerante 2L", 8.90);
        ProdutoDAO dao = new ProdutoDAO();

        // Salva no banco
        dao.salvar(novoProduto);

        // Lista do banco
        dao.listarTodos().forEach(p -> {
            System.out.println(p.getNome() + " - R$ " + p.getPreco());
        });
    }
}