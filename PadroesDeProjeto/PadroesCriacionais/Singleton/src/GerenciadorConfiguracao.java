public class GerenciadorConfiguracao {
    
    // 1. A única instância da classe
    private static GerenciadorConfiguracao instancia;
    
    private String nomeDoServidor;

    // 2. Construtor privado: impede o uso de "new GerenciadorConfiguracao()"
    private GerenciadorConfiguracao() {
        this.nomeDoServidor = "Servidor-Producao-01";
        System.out.println("Configurações carregadas com sucesso!");
    }

    // 3. Método global para acessar a instância
    public static GerenciadorConfiguracao getInstance() {
        if (instancia == null) {
            instancia = new GerenciadorConfiguracao();
        }
        return instancia;
    }

    public String getNomeDoServidor() {
        return nomeDoServidor;
    }
}