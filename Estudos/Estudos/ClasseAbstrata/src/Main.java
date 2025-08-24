public class Main {
    public static void main(String[] args) {
        // Criando instâncias das classes concretas com todos os atributos
        Cachorro meuCachorro = new Cachorro("Rex", "Pastor Alemão", 5, 30.5);
        Gato meuGato = new Gato("Frajola", "Siamês", 3, 4.2);

        // Chamando os métodos e acessando os atributos
        System.out.println("\n--- Dados do Cachorro ---");
        System.out.println("Nome: " + meuCachorro.getNome());
        System.out.println("Raça: " + meuCachorro.getRaca());
        System.out.println("Idade: " + meuCachorro.getIdadeAproximada() + " anos");
        System.out.println("Peso: " + meuCachorro.getPeso() + " kg");
        meuCachorro.comer();
        meuCachorro.emitirSom();

        // Usando um setter para alterar o nome e o peso do cachorro
        meuCachorro.setNome("Max");
        meuCachorro.setPeso(32.1);

        System.out.println("\n--- Dados do Cachorro Após o Petisco ---");
        System.out.println("Nome: " + meuCachorro.getNome());
        System.out.println("Peso: " + meuCachorro.getPeso() + " kg");

        System.out.println("\n--- Dados do Gato ---");
        System.out.println("Nome: " + meuGato.getNome());
        System.out.println("Raça: " + meuGato.getRaca());
        System.out.println("Idade: " + meuGato.getIdadeAproximada() + " anos");
        System.out.println("Peso: " + meuGato.getPeso() + " kg");
        meuGato.comer();
        meuGato.emitirSom();
    }
}