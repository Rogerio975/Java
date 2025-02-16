import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {
        var artigo = new Artigo("Java 11", "José da Silva", LocalDate.of(2021, 1, 1));
        System.out.println("\nTítulo: " + artigo.getTitulo() + ", Autor: " + artigo.getAutor() + ", Data: " + artigo.getDataPublicacao());
    }
}
