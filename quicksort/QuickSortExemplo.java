import java.util.Random;

public class QuickSortExemplo {

    public static void quickSort(int[] vetor, int inicio, int fim) { // Método principal do Quick Sort
        if (inicio < fim) {
            int posicaoPivo = particionar(vetor, inicio, fim); // Encontra a posição do pivô

            quickSort(vetor, inicio, posicaoPivo - 1); // Ordena a parte esquerda do pivô
            quickSort(vetor, posicaoPivo + 1, fim);  // Ordena a parte direita do pivô
        }
    }

    private static int particionar(int[] vetor, int inicio, int fim) { // Método para particionar o vetor
        int pivo = vetor[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) { // Percorre o vetor do início ao fim-1
            if (vetor[j] <= pivo) {
                i++;

                int temp = vetor[i]; // Troca os elementos
                vetor[i] = vetor[j]; // Troca o elemento menor ou igual ao pivô para a posição correta
                vetor[j] = temp; // Troca o elemento atual para a posição correta
            }
        }

        int temp = vetor[i + 1];
        vetor[i + 1] = vetor[fim];
        vetor[fim] = temp;

        return i + 1;
    }

    public static void main(String[] args) {
        int[] numeros = new int[100]; // Array para armazenar os números aleatórios
        Random random = new Random(); // Objeto para gerar números aleatórios

        // Gera 100 números aleatórios
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = random.nextInt(1000); // Números entre 0 e 999
        }

        System.out.println("Antes da ordenação:");
        for (int n : numeros) {
            System.out.print(n + " ");
        }

        quickSort(numeros, 0, numeros.length - 1);

        System.out.println("\n\nDepois da ordenação:");
        for (int n : numeros) {
            System.out.print(n + " ");
        }
    }
}