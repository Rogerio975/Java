public class FuncionarioProprio extends Funcionario {
    private double bonus;

    public FuncionarioProprio(String nome, String cargo, double salario, double bonus) {
        super(nome, cargo, salario);
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double getSalario() {
        return super.getSalario() + bonus;
    }
        

}
