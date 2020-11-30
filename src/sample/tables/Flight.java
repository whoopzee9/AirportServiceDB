package sample.tables;

import java.sql.Timestamp;

public class Flight {
    private String flightCode;
    private String departure;
    private String destination;
    private Timestamp departureDate;
    private Timestamp arrivalDate;
    private String planeName;
    private String airlineName;
    private String status;
    private Double basePrice;

    public Flight(String flightCode, String departure, String destination, Timestamp departureDate,
                  Timestamp arrivalDate, String planeName, String airlineName, String status, Double basePrice) {
        this.flightCode = flightCode;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.planeName = planeName;
        this.airlineName = airlineName;
        this.status = status;
        this.basePrice = basePrice;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return flightCode + ": " + departure + " - " + destination;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Flight flight = (Flight) obj;

        return flightCode.equals(flight.flightCode) && departure.equals(flight.departure) && destination.equals(flight.destination)
                && departureDate.equals(flight.departureDate) && arrivalDate.equals(flight.arrivalDate) && planeName.equals(flight.planeName)
                && airlineName.equals(flight.airlineName) && status.equals(flight.status) && basePrice.equals(flight.basePrice);
    }
}
