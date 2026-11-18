public class ExemploThreads {
    public static void main(String[] args) {
        // 1. Thread tradicional via Runnable (Lambda)
        Thread threadPlataforma = new Thread(() -> {
            System.out.println("Executando na Platform Thread: " + Thread.currentThread().getName());
            System.out.println("Thread ID: " + Thread.currentThread().threadId());
        });
        threadPlataforma.start(); // Inicia a nova thread (não chame run() diretamente)

        // 2. Virtual Thread (Java 21+)
        Thread.ofVirtual().start(() -> {
            System.out.println("Executando na Virtual Thread: " + Thread.currentThread());
        });
        try {
            threadPlataforma.join(); // Aguarda a thread tradicional terminar antes de encerrar o main
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
};
