package com.exemplo.meu_site_java;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        // Envia dados do Java para a página HTML
        model.addAttribute("mensagem", "Bem-vindo ao meu site feito em Java!");
        model.addAttribute("usuario", "Desenvolvedor Java");
        LocalDateTime agora = LocalDateTime.now();
        model.addAttribute("data", agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.addAttribute("horario", agora.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        
        // Retorna o nome do arquivo HTML (sem a extensão .html)
        return "index"; 
    }
}