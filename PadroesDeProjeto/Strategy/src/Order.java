/**
 * Classe Pedido (Order)
 * Utiliza a estratégia de envio para calcular o frete
 */
public class Order {
    private String productName;
    private double weight;         // em kg
    private double distance;       // em km
    private ShippingStrategy shippingStrategy;
    
    public Order(String productName, double weight, double distance) {
        this.productName = productName;
        this.weight = weight;
        this.distance = distance;
    }
    
    /**
     * Define qual estratégia de envio será utilizada
     * Isso pode mudar em tempo de execução
     */
    public void setShippingStrategy(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }
    
    /**
     * Calcula o valor do frete usando a estratégia definida
     */
    public double calculateShippingCost() {
        if (shippingStrategy == null) {
            throw new IllegalStateException("Estratégia de envio não foi definida!");
        }
        return shippingStrategy.calculateShipping(weight, distance);
    }
    
    /**
     * Exibe o resumo do pedido com frete
     */
    public void displayOrderSummary() {
        System.out.println("\n========== RESUMO DO PEDIDO ==========");
        System.out.println("Produto: " + productName);
        System.out.println("Peso: " + weight + " kg");
        System.out.println("Distância: " + distance + " km");
        System.out.println("Transportadora: " + shippingStrategy.getCompanyName());
        System.out.println("Frete: R$ " + String.format("%.2f", calculateShippingCost()));
        System.out.println("======================================\n");
    }
    
    // Getters
    public String getProductName() {
        return productName;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public double getDistance() {
        return distance;
    }
}
