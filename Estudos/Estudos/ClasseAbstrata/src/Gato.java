// Classe concreta Gato
public class Gato extends Animal {

    // Construtor que chama o construtor da classe pai (Animal)
    public Gato(String nome, String raca, int idadeAproximada, double peso) {
        super(nome, raca, idadeAproximada, peso);
    }

    // Implementação do método abstrato emitirSom()
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " (da raça " + getRaca() + ") diz: Miau!");
    }
}