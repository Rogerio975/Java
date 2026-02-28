public class Main {
    public static void main(String[] args) throws Exception {
        // Criando um pedido de exemplo
        Order order = new Order("Laptop Dell Inspiron", 2.5, 150);
        
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE CÁLCULO DE FRETE - PADRÃO STRATEGY ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        // Testando com SEDEX
        System.out.println("\n1️⃣  OPÇÃO 1: SEDEX");
        order.setShippingStrategy(new SedexStrategy());
        order.displayOrderSummary();
        
        // Testando com PAC
        System.out.println("2️⃣  OPÇÃO 2: PAC");
        order.setShippingStrategy(new PACStrategy());
        order.displayOrderSummary();
        
        // Testando com LOGGI
        System.out.println("3️⃣  OPÇÃO 3: LOGGI");
        order.setShippingStrategy(new LoggiStrategy());
        order.displayOrderSummary();
        
        // Comparando preços
        System.out.println("\n" + "═".repeat(50));
        System.out.println("COMPARAÇÃO DE PREÇOS");
        System.out.println("═".repeat(50));
        
        double sedexCost = calculateCost(order, new SedexStrategy());
        double pacCost = calculateCost(order, new PACStrategy());
        double loggiCost = calculateCost(order, new LoggiStrategy());
        
        System.out.printf("Sedex: R$ %.2f%n", sedexCost);
        System.out.printf("PAC:   R$ %.2f%n", pacCost);
        System.out.printf("Loggi: R$ %.2f (MAIS ECONÔMICO!)%n", loggiCost);
        System.out.println("═".repeat(50));
        
        // Testando com outro pedido
        System.out.println("\n\n4️⃣  NOVO PEDIDO: Livro Didático");
        Order order2 = new Order("Livro Didático Java", 0.8, 50);
        order2.setShippingStrategy(new PACStrategy());
        order2.displayOrderSummary();
    }
    
    private static double calculateCost(Order order, ShippingStrategy strategy) {
        order.setShippingStrategy(strategy);
        return order.calculateShippingCost();
    }
}
