import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class Calendario {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ano = 0;

        // 1. Entrada de dados com validação
        while (true) {
            System.out.print("Digite o ano que você deseja visualizar: ");
            if (scanner.hasNextInt()) {
                ano = scanner.nextInt();
                break;
            } else {
                System.out.println("Por favor, digite um ano válido usando apenas números.");
                scanner.next(); // Limpa a entrada incorreta
            }
        }

        System.out.printf("%n--- CALENDÁRIO DE %d ---%n", ano);

        // Define o local para garantir que os nomes fiquem em português
        Locale localBrasil = Locale.forLanguageTag("pt-BR");

        // 2. Loop principal pelos 12 meses
        for (int mes = 1; mes <= 12; mes++) {
            YearMonth anoMes = YearMonth.of(ano, mes);
            
            // Pega o nome do mês formatado em português e coloca a primeira letra em maiúscula
            String nomeMes = anoMes.getMonth().getDisplayName(TextStyle.FULL, localBrasil);
            nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);
            
            System.out.printf("%n=== %s ===%n", nomeMes);
            System.out.println("Dom Seg Ter Qua Qui Sex Sáb");

            // Descobre o dia da semana em que o mês começa
            LocalDate primeiroDia = anoMes.atDay(1);
            // O Java considera Segunda=1 ... Sábado=6, Domingo=7. 
            // Ajustamos para o nosso padrão de tela (Domingo=0, Segunda=1...)
            int primeiroDiaSemana = primeiroDia.getDayOfWeek().getValue() % 7;

            // Descobre o total de dias do mês atual (trata anos bissextos automaticamente)
            int totalDias = anoMes.lengthOfMonth();

            // Imprime os espaços em branco iniciais para alinhar o primeiro dia
            for (int i = 0; i < primeiroDiaSemana; i++) {
                System.out.print("    ");
            }

            // 3. Imprime os dias do mês
            for (int dia = 1; dia <= totalDias; dia++) {
                // %3d garante que o número ocupe 3 espaços, mantendo as colunas alinhadas
                System.out.printf("%3d ", dia);

                // Se chegou no sábado ou é o último dia do mês, quebra a linha
                if ((dia + primeiroDiaSemana) % 7 == 0 || dia == totalDias) {
                    System.out.println();
                }
            }
        }

        scanner.close();
    }
}