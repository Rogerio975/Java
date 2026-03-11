
public class FuncionarioTerceirizado extends Funcionario {
    private final Double despesaAdicional;

    public FuncionarioTerceirizado(String nome, Integer horasTrabalhadas, Double valorHora, Double despesaAdicional) {
        super(nome, horasTrabalhadas, valorHora);
        this.despesaAdicional = despesaAdicional;
    }

    @Override
    public Double calcularPagamento() {
        // Exemplo: Pagamento + 110% da despesa adicional
        return (valorHora * horasTrabalhadas) + (despesaAdicional * 1.1);
    }
}