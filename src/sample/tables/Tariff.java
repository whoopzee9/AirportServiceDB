package sample.tables;

public class Tariff {
    private Double basePrice;

    public Tariff(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return basePrice.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Tariff tariff = (Tariff) obj;

        return tariff.basePrice.equals(this.basePrice);
    }
}
