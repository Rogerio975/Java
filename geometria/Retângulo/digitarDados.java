import java.util.Scanner;

public class digitarDados {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Digite a largura do retângulo: ");
            double largura = scanner.nextDouble();
            
            System.out.print("Digite a altura do retângulo: ");
            double altura = scanner.nextDouble();
            
            double area = largura * altura;
            double perimetro = 2 * (largura + altura);
            
            System.out.println("Área do retângulo: " + area);
            System.out.println("Perímetro do retângulo: " + perimetro);
        }
    }
}