package decorator;

/**
 * ConcreteDecorator que adiciona leite ao café.
 */
public class ComLeite extends CafeDecorator {

    public ComLeite(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + ", com leite";
    }

    @Override
    public double getCusto() {
        return super.getCusto() + 1.50;
    }
}