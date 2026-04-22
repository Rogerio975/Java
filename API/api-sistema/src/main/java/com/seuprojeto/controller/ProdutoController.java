package com.seuprojeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seuprojeto.model.Produto;
import com.seuprojeto.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos") // Define a rota base
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository; // Injeção de dependência (o Spring gerencia isso)

    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return repository.save(produto);
    }
}
