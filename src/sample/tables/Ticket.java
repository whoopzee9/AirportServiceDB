package sample.tables;

import java.sql.Date;

public class Ticket {
    private String name;
    private Date birthDate;
    private String passport;
    private String flightCode;
    private Integer seat;
    private String serviceClass;
    private Double price;
    private String cashierName;

    public Ticket(String name, Date birthDate, String passport, String flightCode, int seat, String serviceClass, double price, String cashierName) {
        this.name = name;
        this.birthDate = birthDate;
        this.passport = passport;
        this.flightCode = flightCode;
        this.seat = seat;
        this.serviceClass = serviceClass;
        this.price = price;
        this.cashierName = cashierName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) obj;

        return name.equals(ticket.name) && birthDate.equals(ticket.birthDate) && passport.equals(ticket.passport)
                && flightCode.equals(ticket.flightCode) && seat.equals(ticket.seat) && serviceClass.equals(ticket.serviceClass)
                && price.equals(ticket.price) && cashierName.equals(ticket.cashierName);
    }
}
