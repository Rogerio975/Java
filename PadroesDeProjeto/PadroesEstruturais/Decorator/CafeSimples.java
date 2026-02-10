package decorator;

/**
 * O ConcreteComponent é o objeto original ao qual funcionalidades extras
 * serão adicionadas.
 */
public class CafeSimples implements Cafe {
    @Override
    public String getDescricao() {
        return "Café simples";
    }

    @Override
    public double getCusto() {
        return 5.0;
    }
}