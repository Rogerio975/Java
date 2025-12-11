import java.util.Scanner;

public class Eleicoes {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite sua idade: ");
        int idade = scanner.nextInt();
        
        // Voto obrigatório: >= 18 e < 70
        if (idade >= 18 && idade < 70) {
            System.out.println("Sua idade é " + idade + " anos.");
            System.out.println("O VOTO É OBRIGATÓRIO!");
        }
        // Voto facultativo: >= 16 e < 18, ou >= 70
        else if ((idade >= 16 && idade < 18) || idade >= 70) {
            System.out.println("Sua idade é " + idade + " anos.");
            System.out.println("O VOTO É FACULTATIVO!");
        }
        // Voto não permitido
        else {
            System.out.println("Sua idade é " + idade + " anos.");
            System.out.println("O VOTO NÃO É PERMITIDO!");
        }
        
        scanner.close();
    }
}
