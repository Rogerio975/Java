import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {
        // Objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);
        double num1, num2, resultado;
        char operador;

        System.out.println("\n--- Calculadora Básica em Java (Terminal) ---");

        // 1. Receber o primeiro número
        System.out.print("Digite o primeiro número: ");
        // Garante que a entrada seja um número e armazena
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Digite um número.");
            scanner.next(); // Limpa a entrada inválida
            System.out.print("Digite o primeiro número: ");
        }
        num1 = scanner.nextDouble();

        // 2. Receber o operador
        System.out.print("Escolha o operador (+, -, *, /): ");
        operador = scanner.next().charAt(0); // Pega o primeiro caractere da entrada

        // 3. Receber o segundo número
        System.out.print("Digite o segundo número: ");
         while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Digite um número.");
            scanner.next(); // Limpa a entrada inválida
            System.out.print("Digite o segundo número: ");
        }
        num2 = scanner.nextDouble();

        // 4. Realizar a operação usando switch-case
        switch (operador) {
            case '+':
                resultado = num1 + num2;
                System.out.println("Resultado: " + num1 + " + " + num2 + " = " + resultado);
                break;

            case '-':
                resultado = num1 - num2;
                System.out.println("Resultado: " + num1 + " - " + num2 + " = " + resultado);
                break;

            case '*':
                resultado = num1 * num2;
                System.out.println("Resultado: " + num1 + " * " + num2 + " = " + resultado);
                break;

            case '/':
                if (num2 != 0) {
                    resultado = num1 / num2;
                    System.out.println("Resultado: " + num1 + " / " + num2 + " = " + resultado);
                } else {
                    System.out.println("Erro: Divisão por zero não é permitida.");
                }
                break;

            default:
                System.out.println("Erro: Operador inválido.");
                break;
        }

        // Fechar o objeto Scanner para liberar recursos
        scanner.close();
    }
}