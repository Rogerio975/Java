public class Aviao implements Transport {
    @Override
    public void entregar(String destino) {
        System.out.println("[Avião] Carregando carga e voando até " + destino + "...");
        System.out.println("[Avião] Entrega concluída em " + destino + ".");
    }
}