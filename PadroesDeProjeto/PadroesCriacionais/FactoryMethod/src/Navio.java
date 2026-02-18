public class Navio implements Transport {
    @Override
    public void entregar(String destino) {
        System.out.println("[Navio] Carregando contêineres e navegando até " + destino + "...");
        System.out.println("[Navio] Entrega concluída em " + destino + ".");
    }
}
