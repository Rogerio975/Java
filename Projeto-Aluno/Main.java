public class Main {
    public static void main(String[] args) {
        Aluno aluno = new Aluno();
        aluno.inserirNome("João");
        System.out.println("Nome do aluno: " + aluno.recuperarNome());
    }
}