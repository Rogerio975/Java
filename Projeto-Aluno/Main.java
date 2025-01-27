import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Aluno aluno = new Aluno();
        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        aluno.inserirNome(nome);

        System.out.println("Nome do aluno: " + aluno.recuperarNome());
    }
}
