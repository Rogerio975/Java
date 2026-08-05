import java.util.HashMap;

public class ExemploHash {
    public static void main(String[] args) {
        // 1. Criando a tabela hash (Chave: String, Valor: Integer)
        HashMap<String, Integer> estoque = new HashMap<>();

        // 2. Inserindo dados - Tempo O(1)
        estoque.put("Arroz", 50);
        estoque.put("Feijão", 30);
        estoque.put("Macarrão", 20);
        estoque.put("Farinha de Trigo", 80);

        // 3. Buscando um valor pela chave - Tempo O(1)
        if (estoque.containsKey("Feijão")) {
            int qtdFeijao = estoque.get("Feijão");
            System.out.println("Quantidade de Feijão: " + qtdFeijao);
        }

        // 4. Removendo um item - Tempo O(1)
        estoque.remove("Macarrão");

        // 5. Imprimindo toda a tabela
        System.out.println("Estoque atual: " + estoque);
    }
}