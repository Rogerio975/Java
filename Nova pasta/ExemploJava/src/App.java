public class App {
    public static void main(String[] args) {
        FuncionarioProprio funcionario1 = new FuncionarioProprio("João Silva", "123.456.789-00", "Gerente", 5000.00);
        FuncionarioProprio funcionario2 = new FuncionarioProprio("Maria Souza", "987.654.321-00", "Analista", 3000.00);

        System.out.println("Funcionário 1:");
        System.out.println("Nome: " + funcionario1.getNome());
        System.out.println("CPF: " + funcionario1.getCpf());
        System.out.println("Cargo: " + funcionario1.getCargo());
        System.out.println("Salário: R$ " + funcionario1.getSalario());
        
        System.out.println("\nFuncionário 2:");
        System.out.println("Nome: " + funcionario2.getNome());
        System.out.println("CPF: " + funcionario2.getCpf());
        System.out.println("Cargo: " + funcionario2.getCargo());
        System.out.printf("Salário: R$ %.2f%n", funcionario2.getSalario());
    }
}
