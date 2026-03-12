public class FuncionarioTerceirizado extends Funcionario {
    public FuncionarioTerceirizado(String nome, String cargo, double salario) {
        super(nome, cargo, salario);
    }

    @Override
    public double getSalario() {
        return super.getSalario();
    }

}
