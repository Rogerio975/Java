// Este programa exibe a data e hora atual formatada no console.
// É mais moderno e flexível.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataHoraAtual {
    public static void main(String[] args) {
        // Obtém data e hora atual
        LocalDateTime agora = LocalDateTime.now();

        // Formata a data e hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Exibe no console
        System.out.println("Data e Hora Atual: " + agora.format(formato));
    }
}