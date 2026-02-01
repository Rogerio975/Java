public class App {
    public static void main(String[] args) {
        System.out.println("Sistema de Logística - Factory Method\n");

        TransporteFactory factory = new CaminhaoFactory();
        factory.enviar("São Paulo");

        // O negócio cresce: agora precisamos entregar por navio
        factory = new NavioFactory();
        factory.enviar("Porto do Rio de Janeiro");
    }
}

