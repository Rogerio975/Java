package decorator;

public class App {
    public static void main(String[] args) {
        // Começamos com um café simples
        Cafe meuCafe = new CafeSimples();
        System.out.printf("%s - Custo: R$%.2f%n", meuCafe.getDescricao(), meuCafe.getCusto());

        // Agora decoramos o café com leite
        meuCafe = new ComLeite(meuCafe);
        System.out.printf("%s - Custo: R$%.2f%n", meuCafe.getDescricao(), meuCafe.getCusto());

        // E também com açúcar
        meuCafe = new ComAcucar(meuCafe);
        System.out.printf("%s - Custo: R$%.2f%n", meuCafe.getDescricao(), meuCafe.getCusto());

        System.out.println("\n----------------------------------\n");

        // Podemos criar outra combinação dinamicamente
        // Um café simples com dois açúcares, mas sem leite
        Cafe outroCafe = new CafeSimples();
        outroCafe = new ComAcucar(outroCafe);
        outroCafe = new ComAcucar(outroCafe); // Adicionando açúcar novamente
        System.out.printf("%s - Custo: R$%.2f%n", outroCafe.getDescricao(), outroCafe.getCusto());
    }
}
