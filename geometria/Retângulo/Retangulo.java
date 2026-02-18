public class Retangulo {
    private double largura;
    private double altura;

    // Construtor
    public Retangulo(double largura, double altura) {
        if (largura <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Largura e altura devem ser maiores que zero");
        }
        this.largura = largura;
        this.altura = altura;
    }

    // Getter para largura
    public double getLargura() {
        return largura;
    }

    // Setter para largura
    public void setLargura(double largura) {
        if (largura <= 0) {
            throw new IllegalArgumentException("Largura deve ser maior que zero");
        }
        this.largura = largura;
    }

    // Getter para altura
    public double getAltura() {
        return altura;
    }

    // Setter para altura
    public void setAltura(double altura) {
        if (altura <= 0) {
            throw new IllegalArgumentException("Altura deve ser maior que zero");
        }
        this.altura = altura;
    }

    // Calcula a área do retângulo
    public double calcularArea() {
        return largura * altura;
    }

    // Calcula o perímetro do retângulo
    public double calcularPerimetro() {
        return 2 * (largura + altura);
    }

    // Retorna informações do retângulo
    @Override
    public String toString() {
        return String.format("Retângulo: largura=%.2f, altura=%.2f, área=%.2f, perímetro=%.2f",
                largura, altura, calcularArea(), calcularPerimetro());
    }
}
