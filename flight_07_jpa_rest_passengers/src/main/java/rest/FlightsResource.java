package rest;

import entity.Flight;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Singleton;

import ejb.FlightService;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("flights")
@Singleton
public class FlightsResource {

	/*
	 * <flight> 
	 * <flightNumber>1234</flightNumber>
	 * <fromAirport>from</fromAirport> 
	 * <toAirport>to</toAirport>
	 * </flight>
	 */

	@EJB
	FlightService flightService;

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Flight> getFlights() {
		return flightService.getFlights();
	}

	@PUT
	@Consumes("application/xml")
	@Produces({ "application/xml", "application/json" })
	public Flight putFlight(Flight flight) {
		return flightService.addFlight(flight);
	}
}
