package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String flightNumber;
    private String fromAirport; // from is a SQL keyword and therefore can not be used!
    private String toAirport;

    public Flight() {
    }

    public Flight(String flightNumber, String fromAirport, String toAirport) {
        this.flightNumber = flightNumber;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
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

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (flightNumber != null ? !flightNumber.equals(flight.flightNumber) : flight.flightNumber != null) return false;
        if (fromAirport != null ? !fromAirport.equals(flight.fromAirport) : flight.fromAirport != null) return false;
        return toAirport != null ? toAirport.equals(flight.toAirport) : flight.toAirport == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (flightNumber != null ? flightNumber.hashCode() : 0);
        result = 31 * result + (fromAirport != null ? fromAirport.hashCode() : 0);
        result = 31 * result + (toAirport != null ? toAirport.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", from='" + fromAirport + '\'' +
                ", to='" + toAirport + '\'' +
                '}';
    }
}
