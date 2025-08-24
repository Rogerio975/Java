public abstract class Animal {
    // Atributos comuns a todos os animais
    private String nome;
    private String raca;
    private int idadeAproximada;
    private double peso;

    // Construtor
    public Animal(String nome, String raca, int idadeAproximada, double peso) {
        this.nome = nome;
        this.raca = raca;
        this.idadeAproximada = idadeAproximada;
        this.peso = peso;
    }

    // Método abstrato - deve ser implementado por cada subclasse
    public abstract void emitirSom();

    // Método concreto - pode ser usado por todas as subclasses
    public void comer() {
        System.out.println(this.nome + " está comendo.");
    }

    // Getters para os atributos
    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdadeAproximada() {
        return idadeAproximada;
    }

    public double getPeso() {
        return peso;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setIdadeAproximada(int idadeAproximada) {
        this.idadeAproximada = idadeAproximada;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}