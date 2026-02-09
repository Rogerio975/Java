import java.util.ArrayList;
import java.util.List;

class Professor {
    private final String nome;

    public Professor(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

class Escola {
    private final String nome;
    private final List<Professor> professores; // Agregação: Escola usa Professores

    public Escola(String nome) {
        this.nome = nome;
        this.professores = new ArrayList<>();
    }

    public void adicionarProfessor(Professor professor) {
        professores.add(professor);
    }

    public void listarProfessores() {
        System.out.println("Professores da escola " + nome + ":");
        for (Professor p : professores) {
            System.out.println("- " + p.getNome());
        }
    }
}

public class App {
    public static void main(String[] args) {
        Professor prof1 = new Professor("Maria");
        Professor prof2 = new Professor("João");

        Escola escola = new Escola("Escola Modelo");
        escola.adicionarProfessor(prof1);
        escola.adicionarProfessor(prof2);

        escola.listarProfessores();
    }
}