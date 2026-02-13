public class ExecutaSingleton {
    public static void main(String[] args) {
        // Tentativa de criar novo objeto (Isso daria erro de compilação):
        // GerenciadorConfiguracao config = new GerenciadorConfiguracao();

        // Forma correta:
        GerenciadorConfiguracao g1 = GerenciadorConfiguracao.getInstance();
        GerenciadorConfiguracao g2 = GerenciadorConfiguracao.getInstance();

        System.out.println(g1.getNomeDoServidor());

        // Verificação de identidade: os dois apontam para o mesmo lugar na memória?
        if (g1 == g2) {
            System.out.println("Ambas as variáveis são a mesma instância!");
        }
    }
}