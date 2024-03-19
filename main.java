public class main {
    public class Main {
        public static void main(String[] args) {
            Veiculo carro = new Veiculo("Toyota", "Corolla", "Preto", 4, "Gasolina");
    
            System.out.println("Detalhes do carro:");
            System.out.println("Marca: " + carro.getMarca());
            System.out.println("Modelo: " + carro.getModelo());
            System.out.println("Cor: " + carro.getCor());
            System.out.println("Quantidade de portas: " + carro.getQuantidadePortas());
            System.out.println("Tipo de motor: " + carro.getTipoCombustivel());
        }
    }    
}
