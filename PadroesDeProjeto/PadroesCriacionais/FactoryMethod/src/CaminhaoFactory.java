public class CaminhaoFactory extends TransporteFactory {
    @Override
    public Transport criarTransporte() {
        return new Caminhao();
    }
}
