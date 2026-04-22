package com.seuprojeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seuprojeto.model.Produto;
import com.seuprojeto.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        // Exemplo de Regra de Negócio: Impedir preços negativos
        if (produto.getPreco() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        
        // Aqui você poderia adicionar outras lógicas, como formatar o nome
        produto.setNome(produto.getNome().toUpperCase());
        
        return repository.save(produto);
    }
}