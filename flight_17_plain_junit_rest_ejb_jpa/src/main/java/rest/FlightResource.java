package rest;

import entity.Flight;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import service.FlightService;

@Path("flights")
@Singleton
public class FlightResource {

    @EJB
    private FlightService service;

    @Context
    private UriInfo context;

    @GET
    @Produces("application/json")
    public List<Flight> getFlights() {
        return service.getFlights();
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public Flight getFlight(@PathParam("id") long id) {
        if (!service.exists(id))
            throw new NotFoundException("flight with id:" + id + " does not exist.");
        return service.getFlight(id);
    }

    @DELETE
    @Path("{id}")
    public void deleteFlight(@PathParam("id") long id) {
        if (!service.exists(id))
            throw new NotFoundException("flight with id:" + id + " does not exist.");
        service.removeFlight(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Flight createFlight(Flight flight) {
        return service.addFlight(flight);
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}")
    public Flight updateFlight(@PathParam("id") long id, Flight flight) {
        if (!service.exists(id))
            throw new NotFoundException("flight with id:" + id + " does not exist.");
        return service.updateFlight(id, flight);
    }

}
