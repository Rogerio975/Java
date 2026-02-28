import java.time.LocalTime;

public class Saudacao {

    public static void main(String[] args) {
        // Obtém a hora atual
        LocalTime now = LocalTime.now();
        int hour = now.getHour();

        String greeting;

        if (hour >= 6 && hour < 12) {
            greeting = "Bom dia!";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Boa tarde!";
        } else {
            greeting = "Boa noite!";
        }

        // Exibe a saudação no console
        System.out.println(greeting);
    }
}