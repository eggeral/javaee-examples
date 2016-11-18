package rest;

import entity.Flight;

import java.util.List;
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

    @Inject
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
    public void deleteFlight(@PathParam("id") long id) {
        if (!service.exists(id))
            throw new NotFoundException("flight with id:" + id + " does not exist.");
        service.remove(id);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Flight createFlight(Flight flight) {
        return service.add(flight);
    }
}
