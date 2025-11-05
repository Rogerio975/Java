import java.awt.Desktop;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        String html = "<!doctype html>\n"
                + "<html>\n"
                + "<head>\n"
                + "  <meta charset=\"utf-8\">\n"
                + "  <title>Exemplo Java + HTML</title>\n"
                + "  <style>body { font-family: Arial, Helvetica, sans-serif; padding: 20px; }</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <h1>Olá do Java + HTML</h1>\n"
                + "  <p>Este arquivo HTML foi gerado por <strong>App.java</strong>.</p>\n"
                + "  <ul>\n"
                + "    <li>Data de geração: " + java.time.ZonedDateTime.now() + "</li>\n"
                + "  </ul>\n"
                + "</body>\n"
                + "</html>\n";

        Path out = Paths.get("output.html");
        try {
            Files.write(out, html.getBytes(StandardCharsets.UTF_8));
            System.out.println("Arquivo criado: " + out.toAbsolutePath());

            // Tenta abrir no navegador padrão (funciona no Windows se Desktop suportado)
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(out.toUri());
                System.out.println("Abrindo no navegador padrão...");
            } else {
                System.out.println("A API Desktop não é suportada neste ambiente. Abra '" + out.toAbsolutePath() + "' manualmente.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao gerar/abrir o arquivo HTML: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
