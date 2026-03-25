import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class temporizador extends JFrame {
    private final JLabel labelTempo;
    private final JTextField campoSegundos;
    private final JButton btnIniciar;
    private final JButton btnResetar;
    private final Timer timer;
    private int tempoRestante;

    public temporizador() {
        // Configurações da Janela
        setTitle("Temporizador Java");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Componentes da Interface
        labelTempo = new JLabel("00:00");
        labelTempo.setFont(new Font("Helvetica", Font.BOLD, 48));
        
        campoSegundos = new JTextField("60", 5);
        campoSegundos.setHorizontalAlignment(JTextField.CENTER);

        btnIniciar = new JButton("Iniciar");
        btnResetar = new JButton("Resetar");

        // Adicionando à tela
        add(labelTempo);
        add(new JLabel("Segundos:"));
        add(campoSegundos);
        add(btnIniciar);
        add(btnResetar);

        // Lógica do Timer (executa a cada 1000ms)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tempoRestante > 0) {
                    tempoRestante--;
                    atualizarLabel();
                } else {
                    pararTimer();
                    btnIniciar.setEnabled(true);
                    tocarAlarme();
                    JOptionPane.showMessageDialog(null, "O tempo acabou!");
                }
            }
        });

        // Eventos dos Botões
        btnIniciar.addActionListener(e -> iniciarTimer());
        btnResetar.addActionListener(e -> pararTimer());

        setVisible(true);
    }

    private void atualizarLabel() {
        int minutos = tempoRestante / 60;
        int segundos = tempoRestante % 60;
        labelTempo.setText(String.format("%02d:%02d", minutos, segundos));
    }

    private void iniciarTimer() {
        try {
            tempoRestante = Integer.parseInt(campoSegundos.getText());
            atualizarLabel();
            timer.start();
            btnIniciar.setEnabled(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número válido!");
        }
    }

    private void pararTimer() {
        timer.stop();
        tempoRestante = 0;
        atualizarLabel();
        btnIniciar.setEnabled(true);
    }

    private void tocarAlarme() {
        // Som padrão do sistema (Toolkit)
        Toolkit.getDefaultToolkit().beep();
    }

    public static void main(String[] args) {
        // Garante que a UI rode na thread correta de eventos
        SwingUtilities.invokeLater(() -> new temporizador());
    }
}