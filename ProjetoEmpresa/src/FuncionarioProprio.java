public class FuncionarioProprio extends Funcionario {
    public FuncionarioProprio(String nome, Integer horasTrabalhadas, Double valorHora) {
        super(nome, horasTrabalhadas, valorHora);
    }

    @Override
    public Double calcularPagamento() {
        return valorHora * horasTrabalhadas;
    }
}