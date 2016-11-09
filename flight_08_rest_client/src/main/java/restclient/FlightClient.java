package restclient;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FlightClient {
	private final WebTarget flightWebTarget;

    public FlightClient() {
        Client client = ClientBuilder.newClient();

        flightWebTarget = client.target("http://localhost:8080/flight_8_rest_server")
                .path("webresources")
                .path("flights");

    }

    public List<Flight> getFlights() {
        Response response = flightWebTarget.request(MediaType.APPLICATION_XML).get();
        System.out.println(response.getStatus());
        List<Flight> flights = response.readEntity(new GenericType<List<Flight>>() { });
        System.out.println(flights);
        return flights;
    }

    public Flight getFlight(long id) {
        Response response = flightWebTarget.path(Long.toString(id))
                .request(MediaType.APPLICATION_XML).get();
        System.out.println(response.getStatus());
        if (response.getStatus() != 200)
        	return null;
        Flight flight = response.readEntity(Flight.class);
        System.out.println(flight);
        return flight;
    }

    public boolean deleteFlight(long id) {
        Response response = flightWebTarget.path(Long.toString(id)).request().delete();
        System.out.println(response.getStatus());
        return response.getStatus() == 202;
    }

    public Flight addFlight(Flight flight) {
        Response response = flightWebTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(flight, MediaType.APPLICATION_XML));
        System.out.println(response.getStatus());
        flight = response.readEntity(Flight.class);
        System.out.println(flight);
        return flight;
    }
}
