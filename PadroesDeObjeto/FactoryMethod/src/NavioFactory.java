public class NavioFactory extends TransporteFactory {
    @Override
    public Transport criarTransporte() {
        return new Navio();
    }
}
