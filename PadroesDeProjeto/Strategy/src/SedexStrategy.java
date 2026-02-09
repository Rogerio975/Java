/**
 * Estratégia concreta: Sedex
 * Cobra R$ 30 base + R$ 2 por kg + R$ 0.50 por km
 */
public class SedexStrategy implements ShippingStrategy {
    
    @Override
    public double calculateShipping(double weight, double distance) {
        double baseRate = 30.0;
        double weightRate = 2.0;      // R$ 2 por kg
        double distanceRate = 0.50;   // R$ 0.50 por km
        
        return baseRate + (weight * weightRate) + (distance * distanceRate);
    }
    
    @Override
    public String getCompanyName() {
        return "Sedex (Entrega em 2-3 dias)";
    }
}
