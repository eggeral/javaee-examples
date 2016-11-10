package service;

import entity.Flight;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class FlightService {

    private static List<Flight> flights = new ArrayList<>();
    private static long id = 1;

    static {
        internalAdd(new Flight("OS101", "GRZ", "VIE"));
        internalAdd(new Flight("LH2412", "MNC", "NYN"));
        internalAdd(new Flight("TH112", "BKG", "PKT"));
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight add(Flight flight) {
        return internalAdd(flight);
    }

    public Flight get(long id) {
        return flights.stream()
                .filter(f -> f.getId() == id).findFirst().get();
    }

    private static Flight internalAdd(Flight flight) {
        flight.setId(id++);
        flights.add(flight);
        return flight;
    }

    public void remove(long id) {
        flights.removeIf(f -> f.getId() == id);
    }

    public boolean exists(long id) {
        return flights.stream().anyMatch(flight -> flight.getId() == id);
    }
}
