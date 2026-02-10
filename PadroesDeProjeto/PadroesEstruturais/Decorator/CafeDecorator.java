package decorator;

/**
 * A classe Decorator abstrata segue a mesma interface que o componente que ela
 * decora. O propósito principal desta classe é definir a interface de empacotamento
 * para todos os decoradores concretos.
 */
public abstract class CafeDecorator implements Cafe {
    protected final Cafe cafeDecorado;

    public CafeDecorator(Cafe cafe) {
        this.cafeDecorado = cafe;
    }

    public String getDescricao() {
        return cafeDecorado.getDescricao();
    }

    public double getCusto() {
        return cafeDecorado.getCusto();
    }
}