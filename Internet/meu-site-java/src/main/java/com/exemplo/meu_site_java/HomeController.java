package com.exemplo.meu_site_java;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Envia dados do Java para a página HTML
        model.addAttribute("mensagem", "Bem-vindo ao meu site feito em Java!");
        model.addAttribute("usuario", "Desenvolvedor Java");
        model.addAttribute("ano", 2026);
        
        // Retorna o nome do arquivo HTML (sem a extensão .html)
        return "index"; 
    }
}