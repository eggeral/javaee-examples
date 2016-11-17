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

// Jersey automatically serves webresource/application.wadl RestEasy does not
// https://issues.jboss.org/browse/RESTEASY-166
// http://localhost:8080/flight_3_rest-1.0-SNAPSHOT/webresources/flights

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
        return service.get(id);
    }

    @DELETE
    @Path("{id}")
    public void removeFlight(@PathParam("id") long id) {
        if (!service.exists(id))
            throw new NotFoundException("flight with id:" + id + " does not exist.");
        service.remove(id);
    }

    // Don't forget to set the Content-Type application/xml header of the request!
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Flight putFlight(Flight flight) {
        Flight newFlight = service.add(flight);
        return newFlight;
    }
}
