/**
 * Estratégia concreta: PAC
 * Cobra R$ 15 base + R$ 1 por kg + R$ 0.30 por km
 */
public class PACStrategy implements ShippingStrategy {
    
    @Override
    public double calculateShipping(double weight, double distance) {
        double baseRate = 15.0;
        double weightRate = 1.0;      // R$ 1 por kg
        double distanceRate = 0.30;   // R$ 0.30 por km
        
        return baseRate + (weight * weightRate) + (distance * distanceRate);
    }
    
    @Override
    public String getCompanyName() {
        return "PAC (Entrega em 8-10 dias)";
    }
}
