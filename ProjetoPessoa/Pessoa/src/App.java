public class App {
    public static void main(String[] args) throws Exception {
        // Criando uma instância da classe Pessoa
        Pessoa pessoa = new Pessoa("João", "Masculino", 30);
        Pessoa pessoa2 = new Pessoa("Maria", "Feminino", 25);

        // Exibindo os dados da pessoa
        System.out.println(pessoa);
        System.out.println(pessoa2);
    }
}
