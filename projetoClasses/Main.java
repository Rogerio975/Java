public class Main {
    public static void main(String[] args) {
        try {
            Pessoa pessoa = new Pessoa(1, "João Silva", "20230101", "joao.silva@email.com", "123.456.789-09");
            System.out.println(pessoa);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}