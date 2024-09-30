public class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("João", "Rua das Rosas, 12", "(71) 91234-5678", 32);

        cliente1.imprimirCliente();
        
        Cliente cliente2 = new Cliente("Maria", "Rua das Flores, 20", "(71) 1234-5678", 21);

        cliente2.imprimirCliente();
    }
}