package rest;

import entity.Flight;
import entity.Passenger;

import javax.ejb.EJB;
import javax.inject.Singleton;

import ejb.FlightService;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
    public Response getFlight(@PathParam("flightId") long flightId) {
    	Flight flight = flightService.getFlight(flightId);
    	if (flight == null)
    		return Response.status(404).entity("Not found").build(); 	
    	else
    		return Response.status(200).entity(flight).build();
    }
  
    @PUT
	@Consumes("application/xml")
	@Produces({ "application/xml", "application/json" })
	public Passenger putPassenger(@PathParam("flightId") long flightId, Passenger passenger) {
		return flightService.addPassenger(flightId, passenger);
	}
    
    @DELETE
  	public Response deleteFlight(@PathParam("flightId") long flightId) {
    	try {
    		boolean removed = flightService.removeFlight(flightId);
    		if (removed)
    			return Response.status(202).entity("Deleted successfully !!").build();
			return Response.status(502).entity("Could not delete entity").build();
    	}
    	catch (Exception ex) {
    		return Response.status(500).entity("Could not delete entity").build();		
    	}
    }
}
