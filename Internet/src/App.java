import java.awt.Desktop;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        String html = """
                      <!doctype html>
                      <html>
                      <head>
                        <meta charset="utf-8">
                        <title>Exemplo Java + HTML</title>
                        <style>body { font-family: Arial, Helvetica, sans-serif; padding: 20px; }</style>
                      </head>
                      <body>
                        <h1>Ol\u00e1 do Java + HTML</h1>
                        <p>Este arquivo HTML foi gerado por <strong>App.java</strong>.</p>
                        <ul>
                          <li>Data de gera\u00e7\u00e3o: """ + java.time.ZonedDateTime.now() + "</li>\n"
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
            System.exit(1);
        }
    }
}
