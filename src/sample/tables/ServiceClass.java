package sample.tables;

public class ServiceClass {
    private String name;
    private Double multiplier;

    public ServiceClass(String name, double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ServiceClass serviceClass = (ServiceClass) obj;

        return name.equals(serviceClass.name) && multiplier.equals(serviceClass.multiplier);
    }
}
