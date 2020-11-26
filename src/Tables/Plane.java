package Tables;

public class Plane {
    private String name;
    private int totalSeats;
    private int businessClassSeats;
    private int firstClassSeats;

    public Plane(String name, int totalSeats, int businessClassSeats, int firstClassSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
        this.businessClassSeats = businessClassSeats;
        this.firstClassSeats = firstClassSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getBusinessClassSeats() {
        return businessClassSeats;
    }

    public void setBusinessClassSeats(int businessClassSeats) {
        this.businessClassSeats = businessClassSeats;
    }

    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }
}
