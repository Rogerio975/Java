package src;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class ContadorSemanas {

    public static int getSemanasNoMes(int ano, int mes) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        
        // Define as regras de semana para a região (ex: Brasil / Locale("pt", "BR"))
        // Onde a semana começa no Domingo
        WeekFields weekFields = WeekFields.of(new Locale("pt", "BR"));
        
        // Pega o último dia do mês
        LocalDate ultimoDia = yearMonth.atEndOfMonth();
        
        // Retorna o número da semana dentro do mês para o último dia
        return ultimoDia.get(weekFields.weekOfMonth());
    }

    public static void main(String[] args) {
        int ano = 2026;
        
        // Exemplo: Fevereiro de 2026
        System.out.println("Fevereiro/2026: " + getSemanasNoMes(ano, 2) + " semanas");
        
        // Exemplo: Maio de 2026
        System.out.println("Maio/2026: " + getSemanasNoMes(ano, 5) + " semanas");
    }
}