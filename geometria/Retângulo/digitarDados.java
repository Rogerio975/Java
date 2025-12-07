import java.util.InputMismatchException;
import java.util.Scanner;

public class digitarDados {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            double largura = 0;
            double altura = 0;
            boolean entradaValida = false;
            
            // Lê a largura com validação
            while (!entradaValida) {
                try {
                    System.out.print("Digite a largura do retângulo: ");
                    largura = scanner.nextDouble();
                    
                    if (largura <= 0) {
                        System.out.println("Erro: A largura deve ser maior que zero!");
                        continue;
                    }
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número válido (use ponto como separador decimal)!");
                    scanner.nextLine(); // Limpa a entrada inválida
                }
            }
            
            entradaValida = false;
            
            // Lê a altura com validação
            while (!entradaValida) {
                try {
                    System.out.print("Digite a altura do retângulo: ");
                    altura = scanner.nextDouble();
                    
                    if (altura <= 0) {
                        System.out.println("Erro: A altura deve ser maior que zero!");
                        continue;
                    }
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número válido (use ponto como separador decimal)!");
                    scanner.nextLine(); // Limpa a entrada inválida
                }
            }
            
            double area = largura * altura;
            double perimetro = 2 * (largura + altura);
            
            System.out.println("\n=== Resultado ===");
            System.out.printf("Largura: %.2f%n", largura);
            System.out.printf("Altura: %.2f%n", altura);
            System.out.printf("Área do retângulo: %.2f%n", area);
            System.out.printf("Perímetro do retângulo: %.2f%n", perimetro);
        }
    }
}