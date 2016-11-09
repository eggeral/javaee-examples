package service;

import entity.Flight;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    private List<Flight> flights = new ArrayList<>();
    private long id = 1;

    public FlightService() {
        add(new Flight("OS101", "GRZ", "VIE"));
        add(new Flight("LH2412", "MNC", "NYN"));
        add(new Flight("TH112", "BKG", "PKT"));
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight add(Flight flight) {
        flight.setId(id++);
        flights.add(flight);
        return flight;
    }

    public Flight get(long id) {
        return flights.stream()
                .filter(f -> f.getId() == id).findFirst().get();
    }

    public void remove(long id) {
        flights.removeIf(f -> f.getId() == id);
    }

}
