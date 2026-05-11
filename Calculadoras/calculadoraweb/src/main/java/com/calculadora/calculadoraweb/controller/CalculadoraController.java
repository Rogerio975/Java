package com.calculadora.calculadoraweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculadoraController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/calcular")
    public String calcular(
            @RequestParam double num1,
            @RequestParam double num2,
            @RequestParam String operacao,
            Model model) {

        double resultado = 0;

        switch (operacao) {
            case "+" -> resultado = num1 + num2;
            case "-" -> resultado = num1 - num2;
            case "*" -> resultado = num1 * num2;
            case "/" -> resultado = num1 / num2;
        }

        model.addAttribute("resultado", resultado);

        return "index";
    }
    
}
