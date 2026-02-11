package decorator;

/**
 * ConcreteDecorator que adiciona leite ao café.
 */
public class ComLeite extends CafeDecorator {

    private static final double CUSTO_ADICIONAL = 1.50;

    public ComLeite(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", com leite";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + CUSTO_ADICIONAL;
    }
}