package service;

import entity.Flight;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class FlightService {

    @PersistenceContext(unitName = "flight_16")
    private EntityManager em;

    public List<Flight> getFlights() {
        return em.createQuery("SELECT f from Flight f").getResultList();
    }

    public Flight addFlight(Flight flight) {
        em.persist(flight);
        return flight;
    }

    public Flight getFlight(long id) {
        return em.find(Flight.class, id);
    }

    public void removeFlight(long id) {
        Flight flight = em.find(Flight.class, id);
        em.remove(flight);
    }

    public boolean exists(long id) {
        return em.find(Flight.class, id) != null;
    }

    public Flight updateFlight(long id, Flight flight) {
        Flight oldFlight = em.find(Flight.class, id);
        oldFlight.setFlightNumber(flight.getFlightNumber());
        oldFlight.setFromAirport(flight.getFromAirport());
        oldFlight.setToAirport(flight.getToAirport());

        return oldFlight;
    }
}
