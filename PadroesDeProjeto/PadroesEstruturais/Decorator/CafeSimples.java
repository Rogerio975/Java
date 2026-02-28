package decorator;

/**
 * O ConcreteComponent é o objeto original ao qual funcionalidades extras
 * serão adicionadas.
 */
public class CafeSimples implements Cafe {

    private static final double CUSTO_BASE = 5.0;

    @Override
    public String getDescricao() {
        return "Café simples";
    }

    @Override
    public double getCusto() {
        return CUSTO_BASE;
    }
}