package entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement()
public class Flight {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String flightNumber;
	private String fromAirport; // from is a SQL keyword and therefore can not be used!
	private String toAirport;
	@OneToMany(fetch = FetchType.EAGER)
	private List<Passenger> passengers;

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

	@XmlElementWrapper
	@XmlElement(name="passenger")
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", flightNumber=" + flightNumber + ", fromAirport=" + fromAirport + ", toAirport="
				+ toAirport + "]";
	}


}
