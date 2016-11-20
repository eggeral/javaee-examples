package rest;

import entity.Flight;
import service.FlightService;

import javax.ejb.EJB;
import javax.inject.Singleton;
import javax.ws.rs.*;
import java.util.List;

@Path("flights")
@Singleton
public class FlightResource {

    private FlightService service;

    @EJB
    void setFlightService(FlightService service) { //intentional default access
        this.service = service;
    }

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
