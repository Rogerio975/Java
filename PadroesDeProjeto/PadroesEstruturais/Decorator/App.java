package decorator;

public class App {
    public static void main(String[] args) {
        // Começamos com um café simples
        Cafe meuCafe = new CafeSimples();
        System.out.println(meuCafe.getDescricao() + " - Custo: R$" + meuCafe.getCusto());

        // Agora decoramos o café com leite
        meuCafe = new ComLeite(meuCafe);
        System.out.println(meuCafe.getDescricao() + " - Custo: R$" + meuCafe.getCusto());

        // E também com açúcar
        meuCafe = new ComAcucar(meuCafe);
        System.out.println(meuCafe.getDescricao() + " - Custo: R$" + meuCafe.getCusto());

        System.out.println("\n----------------------------------\n");

        // Podemos criar outra combinação dinamicamente
        // Um café simples com dois açúcares, mas sem leite
        Cafe outroCafe = new CafeSimples();
        outroCafe = new ComAcucar(outroCafe);
        outroCafe = new ComAcucar(outroCafe); // Adicionando açúcar novamente
        System.out.println(outroCafe.getDescricao() + " - Custo: R$" + outroCafe.getCusto());
    }
}
