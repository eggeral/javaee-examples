package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Flight;
import entity.Passenger;

@Stateless
@LocalBean
public class FlightService {

	@PersistenceContext(unitName = "flight_a_jfx_server")
	private EntityManager em;

	public String helloWorld() {
		return "Hello World";
	}

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

    public boolean removeFlight(long id) {
        Flight flight = em.find(Flight.class, id);
        if (flight == null)
        	return false;
        em.remove(flight);
        return true;
    }

	public Passenger addPassenger(long flightId, Passenger passenger) {
		em.persist(passenger);
		Flight flight = em.find(Flight.class, flightId);
		flight.getPassengers().add(passenger);
		return passenger;
	}

   

}
