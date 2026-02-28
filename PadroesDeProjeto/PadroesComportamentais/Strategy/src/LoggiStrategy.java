/**
 * Estratégia concreta: Loggi
 * Cobra R$ 20 base + R$ 1.50 por kg + R$ 0.40 por km
 * Desconto de 10% para distâncias maiores que 100km
 */
public class LoggiStrategy implements ShippingStrategy {
    
    @Override
    public double calculateShipping(double weight, double distance) {
        double baseRate = 20.0;
        double weightRate = 1.50;     // R$ 1.50 por kg
        double distanceRate = 0.40;   // R$ 0.40 por km
        
        double total = baseRate + (weight * weightRate) + (distance * distanceRate);
        
        // Desconto para distâncias maiores que 100km
        if (distance > 100) {
            total *= 0.9;  // 10% de desconto
        }
        
        return total;
    }
    
    @Override
    public String getCompanyName() {
        return "Loggi (Entrega em 5-7 dias)";
    }
}
