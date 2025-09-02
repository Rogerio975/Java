package Main;

import Cliente.Cliente;

public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Nome", "Endereço", "Telefone", 30);
        System.out.println(cliente.getNome());
    }
}