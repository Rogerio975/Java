public class Principal {
    public static void main(String[] args) {
        // Criando alguns retângulos de exemplo
        Retangulo ret1 = new Retangulo(5, 3);
        Retangulo ret2 = new Retangulo(10, 7.5);
        Retangulo ret3 = new Retangulo(4.5, 6.2);

        // Exibindo informações dos retângulos
        System.out.println("=== Cálculo de Área e Perímetro de Retângulos ===\n");
        
        System.out.println("Retângulo 1:");
        System.out.println("Largura: " + ret1.getLargura());
        System.out.println("Altura: " + ret1.getAltura());
        System.out.println("Área: " + ret1.calcularArea());
        System.out.println("Perímetro: " + ret1.calcularPerimetro());
        System.out.println();

        System.out.println("Retângulo 2:");
        System.out.println("Largura: " + ret2.getLargura());
        System.out.println("Altura: " + ret2.getAltura());
        System.out.println("Área: " + ret2.calcularArea());
        System.out.println("Perímetro: " + ret2.calcularPerimetro());
        System.out.println();

        System.out.println("Retângulo 3:");
        System.out.println("Largura: " + ret3.getLargura());
        System.out.println("Altura: " + ret3.getAltura());
        System.out.println("Área: " + ret3.calcularArea());
        System.out.println("Perímetro: " + ret3.calcularPerimetro());
        System.out.println();

        // Usando toString
        System.out.println("Usando toString():");
        System.out.println(ret1);
        System.out.println(ret2);
        System.out.println(ret3);
    }
}
