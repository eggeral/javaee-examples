package entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Flight {
    private long id;
    private String flightNumber;
    private String from;
    private String to;

    public Flight() {
    }
    
    public Flight(String flightNumber, String from, String to) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (flightNumber != null ? !flightNumber.equals(flight.flightNumber) : flight.flightNumber != null) return false;
        if (from != null ? !from.equals(flight.from) : flight.from != null) return false;
        return to != null ? to.equals(flight.to) : flight.to == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (flightNumber != null ? flightNumber.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
