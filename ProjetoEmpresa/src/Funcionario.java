public abstract class Funcionario {
    private final String nome;
    protected Double valorHora;
    protected Integer horasTrabalhadas;

    public Funcionario(String nome, Integer horasTrabalhadas, Double valorHora) {
        this.nome = nome;
        this.horasTrabalhadas = horasTrabalhadas;
        this.valorHora = valorHora;
    }

    public abstract Double calcularPagamento();

    public String getNome() { return nome; }
}