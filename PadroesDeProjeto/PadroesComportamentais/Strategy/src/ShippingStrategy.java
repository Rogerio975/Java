/**
 * Interface Strategy para cálculo de frete
 * Define o contrato que todas as estratégias de envio devem seguir
 */
public interface ShippingStrategy {
    double calculateShipping(double weight, double distance);
    String getCompanyName();
}
