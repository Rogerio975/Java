public class App {
    public static void main(String[] args) throws Exception {
        FuncionarioProprio funcionario1 = new FuncionarioProprio("Maria", "Gerente", 5000.0, 1000.0);
        FuncionarioTerceirizado funcionario2 = new FuncionarioTerceirizado("Pedro", "Suporte", 2000.0);

        System.out.println("\nFuncionário 1 (Próprio):");
        System.out.println("Nome: " + funcionario1.getNome());
        System.out.println("Cargo: " + funcionario1.getCargo());
        System.out.println("Salário: " + funcionario1.getSalario());

        System.out.println("\nFuncionário 2 (Terceirizado):");
        System.out.println("Nome: " + funcionario2.getNome());
        System.out.println("Cargo: " + funcionario2.getCargo());
        System.out.println("Salário: " + funcionario2.getSalario());
    }
}
