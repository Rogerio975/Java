class Motor {
    public void ligar() {
        System.out.println("Motor ligado!");
    }
}

class Carro {
    private final Motor motor; // Composição: Carro possui Motor

    public Carro() {
        this.motor = new Motor(); // Motor criado dentro do Carro
    }

    public void ligarCarro() {
        motor.ligar();
        System.out.println("Carro ligado!");
    }
}

public class Main {
    public static void main(String[] args) {
        Carro carro = new Carro();
        carro.ligarCarro();
    }
}