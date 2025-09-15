import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login {

    public static void main(String[] args) {
        // Cria o frame (a janela principal)
        JFrame frame = new JFrame("Formulário de Login");

        // Define o tamanho da janela
        frame.setSize(300, 150);

        // Define a operação padrão ao fechar a janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria um painel para organizar os componentes
        JPanel panel = new JPanel();

        // Adiciona um gerenciador de layout ao painel
        panel.setLayout(new FlowLayout());

        // Cria os componentes do formulário
        JLabel userLabel = new JLabel("Usuário:");
        JTextField userText = new JTextField(20);
        JLabel passwordLabel = new JLabel("Senha:");
        JTextField passwordText = new JTextField(20);
        JButton loginButton = new JButton("Login");

        // Adiciona os componentes ao painel
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);

        // Adiciona o painel ao frame
        frame.add(panel);

        // Torna a janela visível
        frame.setVisible(true);

        // Dentro do seu main, adicione o listener ao botão:
    loginButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica que será executada ao clicar no botão
        System.out.println("Usuário: " + userText.getText());
        System.out.println("Senha: " + passwordText.getText());
    }
});
    }
}