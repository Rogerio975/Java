package decorator;

/**
 * ConcreteDecorator que adiciona açúcar ao café.
 */
public class ComAcucar extends CafeDecorator {

    public ComAcucar(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", com açúcar";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 0.50;
    }
}