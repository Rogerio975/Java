public class AviaoFactory extends TransporteFactory {
    @Override
    public Transport criarTransporte() {
        return new Aviao();
    }
}