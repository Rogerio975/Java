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

class Motocicleta {
    private final Motor motor; // Composição: Motocicleta possui Motor

    public Motocicleta() {
        this.motor = new Motor(); // Motor criado dentro da Motocicleta
    }

    public void ligarMotocicleta() {
        motor.ligar();
        System.out.println("Motocicleta ligada!");
    }
}

public class Main {
    public static void main(String[] args) {
        Carro carro = new Carro();
        carro.ligarCarro();
        
        Motocicleta motocicleta = new Motocicleta();
        motocicleta.ligarMotocicleta();
    }
}