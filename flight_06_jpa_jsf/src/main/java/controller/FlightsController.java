package controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ejb.FlightService;
import entity.Flight;

@ManagedBean
@SessionScoped
public class FlightsController {

	@EJB
	FlightService flightService;

	private Flight currentFlight = new Flight();

	public String getHello() {
		return "Hallo world";
	}

	public Flight getCurrentFlight() {
		return currentFlight;
	}

	public void setCurrentFlight(Flight currentFlight) {
		this.currentFlight = currentFlight;
	}

	public String create() {
		flightService.addFlight(currentFlight);
		currentFlight = new Flight();
		return "flights";
	}

	public List<Flight> getFlights() {
		return flightService.getFlights();
	}

	public void remove(Flight flight) {
		flightService.removeFlight(flight.getId());
	}
}
