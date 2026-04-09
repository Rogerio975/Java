// Este programa cria uma janela com um relógio digital que exibe a data e hora atual, atualizando a cada segundo.

import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RelogioSwing {
    public static void main(String[] args) {
        // Criando a janela
        JFrame frame = new JFrame("Relógio Digital");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Criando o rótulo
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(label);
        frame.setVisible(true);

        // Formato da data e hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Timer para atualizar a cada 1 segundo
        Timer timer = new Timer(1000, e -> {
            LocalDateTime agora = LocalDateTime.now();
            label.setText(agora.format(formato));
        });

        timer.start();
    }
}