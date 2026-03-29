import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class transferencia {
    
    /**
     * Transfere (copia) arquivos ou pastas de uma origem para um destino.
     * Em ambientes AD, certifique-se de que o usuário que executa o Java tem permissões NTFS na rede.
     */
    public static void copiarRecursivo(Path origem, Path destino) throws IOException {
        Files.walkFileTree(origem, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path destinoDir = destino.resolve(origem.relativize(dir));
                if (!Files.exists(destinoDir)) {
                    Files.createDirectories(destinoDir);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, destino.resolve(origem.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void main(String[] args) {
        // Exemplo de caminhos UNC comuns em ambientes Active Directory
        Path origem = Paths.get("\\\\SERVIDOR\\Compartilhado\\Origem");
        Path destino = Paths.get("\\\\SERVIDOR\\Compartilhado\\Destino");

        try {
            copiarRecursivo(origem, destino);
            System.out.println("Transferência concluída com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao transferir: " + e.getMessage());
        }
    }

}
