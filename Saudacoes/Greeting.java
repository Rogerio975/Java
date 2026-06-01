import java.time.LocalTime;
import java.util.Scanner;

public class Greeting {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            System.out.print("Digite seu nome: ");
            String nome = scanner.nextLine();

            int hora = LocalTime.now().getHour();

            String saudacao;

            if (hora >= 5 && hora < 12) {
                saudacao = "Bom dia";
            } else if (hora >= 12 && hora < 18) {
                saudacao = "Boa tarde";
            } else {
                saudacao = "Boa noite";
            }

            System.out.println(saudacao + ", " + nome + "!");
        }
    }
}