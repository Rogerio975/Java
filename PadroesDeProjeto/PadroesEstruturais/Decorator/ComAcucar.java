package decorator;

/**
 * ConcreteDecorator que adiciona açúcar ao café.
 */
public class ComAcucar extends CafeDecorator {

    private static final double CUSTO_ADICIONAL = 0.50;

    public ComAcucar(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", com açúcar";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + CUSTO_ADICIONAL;
    }
}