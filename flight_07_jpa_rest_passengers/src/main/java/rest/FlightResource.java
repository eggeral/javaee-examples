package rest;

import entity.Flight;
import entity.Passenger;

import javax.ejb.EJB;
import javax.inject.Singleton;

import ejb.FlightService;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("flights/{flightId}")
@Singleton
public class FlightResource {

	@EJB
	FlightService flightService;
	
    @GET
    @Produces({"application/xml", "application/json"})
    public Flight getFlight(@PathParam("flightId") long flightId) {
        return flightService.getFlight(flightId);
    }
  
    @PUT
	@Consumes("application/xml")
	@Produces({ "application/xml", "application/json" })
	public Passenger putPassenger(@PathParam("flightId") long flightId, Passenger passenger) {
		return flightService.addPassenger(flightId, passenger);
	}
}
