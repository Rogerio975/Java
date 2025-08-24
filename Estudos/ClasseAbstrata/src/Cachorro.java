// Classe concreta Cachorro
public class Cachorro extends Animal {

    // Construtor que chama o construtor da classe pai (Animal)
    public Cachorro(String nome, String raca, int idadeAproximada, double peso) {
        super(nome, raca, idadeAproximada, peso);
    }

    // Implementação do método abstrato emitirSom()
    @Override
    public void emitirSom() {
        System.out.println(getNome() + " (da raça " + getRaca() + ") diz: Au au!");
    }
}