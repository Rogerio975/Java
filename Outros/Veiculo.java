public class Veiculo {
    private String marca;
    private String modelo;
    private String cor;
    private int quantidadePortas;
    private String tipoCombustivel;

    // Construtor
    public Veiculo(String marca, String modelo, String cor, int quantidadePortas, String tipoCombustivel) {
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.quantidadePortas = quantidadePortas;
        this.tipoCombustivel = tipoCombustivel;
    }

    // Métodos de acesso (getters e setters)
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(String tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    // Método toString para representação textual do objeto
    @Override
    public String toString() {
        return "Veiculo{" +
                "marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ", quantidadePortas=" + quantidadePortas +
                ", tipoCombustivel='" + tipoCombustivel + '\'' +
                '}';
    }
}
