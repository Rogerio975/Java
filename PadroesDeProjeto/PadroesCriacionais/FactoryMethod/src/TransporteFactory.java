public abstract class TransporteFactory {
    public abstract Transport criarTransporte();

    public void enviar(String destino) {
        System.out.println("Preparando procedimento de envio...");
        Transport t = criarTransporte();
        t.entregar(destino);
        System.out.println("Processo de envio finalizado.\n");
    }
}
