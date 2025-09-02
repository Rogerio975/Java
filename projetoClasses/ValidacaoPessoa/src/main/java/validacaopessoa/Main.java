/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author E144231
 */
package validacaopessoa;

public class Main {
    public static void main(String[] args) {
        try {
            // Exemplo de criação de uma Pessoa
            Pessoa pessoa = new Pessoa(1, "Maria da Silva", "MAT12345", "maria.silva@email.com", "123.456.789-09");
            System.out.println(pessoa);
        } catch (IllegalArgumentException e) {
            // Captura erros de validação
            System.err.println("Erro ao criar pessoa: " + e.getMessage());
        }
    }
}