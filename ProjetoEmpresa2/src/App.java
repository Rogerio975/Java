public class App {
    public static void main(String[] args) throws Exception {
        Funcionario funcionario1 = new Funcionario("João", "Analista", 3000.0);
        FuncionarioProprio funcionario2 = new FuncionarioProprio("Maria", "Gerente", 5000.0, 1000.0);
        FuncionarioTerceirizado funcionario3 = new FuncionarioTerceirizado("Pedro", "Suporte", 2000.0);

        System.out.println("Funcionário 1:");
        System.out.println("Nome: " + funcionario1.getNome());
        System.out.println("Cargo: " + funcionario1.getCargo());
        System.out.println("Salário: " + funcionario1.getSalario());

        System.out.println("\nFuncionário 2:");
        System.out.println("Nome: " + funcionario2.getNome());
        System.out.println("Cargo: " + funcionario2.getCargo());
        System.out.println("Salário: " + funcionario2.getSalario());

        System.out.println("\nFuncionário 3 (Terceirizado):");
        System.out.println("Nome: " + funcionario3.getNome());
        System.out.println("Cargo: " + funcionario3.getCargo());
        System.out.println("Salário: " + funcionario3.getSalario());
    }
}
