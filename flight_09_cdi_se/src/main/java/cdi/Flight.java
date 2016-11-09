package cdi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Flight {
	
	private long id;
	private String flightNumber;
	private String fromAirport;
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
	public String toString() {
		return "Flight [id=" + id + ", flightNumber=" + flightNumber + ", fromAirport=" + fromAirport + ", toAirport="
				+ toAirport + "]";
	}


}
