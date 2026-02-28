public class Caminhao implements Transport {
    @Override
    public void entregar(String destino) {
        System.out.println("[Caminhão] Carregando carga e dirigindo até " + destino + "...");
        System.out.println("[Caminhão] Entrega concluída em " + destino + ".");
    }
}
