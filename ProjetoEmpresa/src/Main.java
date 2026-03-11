public class Main {
    public static void main(String[] args) {
        Funcionario f1 = new FuncionarioProprio("Rogério", 160, 50.0);
        Funcionario f2 = new FuncionarioTerceirizado("Lucas", 100, 40.0, 200.0);

        System.out.println("Pagamento " + f1.getNome() + ": R$ " + f1.calcularPagamento());
        System.out.println("Pagamento " + f2.getNome() + ": R$ " + f2.calcularPagamento());
    }
}