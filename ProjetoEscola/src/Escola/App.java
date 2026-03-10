package Escola;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("\nAluno");
        Aluno aluno = new Aluno("João", 20, "12345");
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Idade: " + aluno.getIdade());
        System.out.println("Matrícula: " + aluno.getMatricula());
        System.out.println("\nProfessor");
        Professor professor = new Professor("Maria", "Matemática");
        System.out.println("Nome: " + professor.getNome());
        System.out.println("Disciplina: " + professor.getDisciplina());        
        Notas notas = new Notas(aluno, 8.5, 7.0);
        System.out.println("\nNotas");
        System.out.println("Aluno: " + notas.getAluno().getNome());
        System.out.println("Nota 1: " + notas.getNota1());
        System.out.println("Nota 2: " + notas.getNota2());
        System.out.println("Média: " + notas.calcularMedia());
    }
}
