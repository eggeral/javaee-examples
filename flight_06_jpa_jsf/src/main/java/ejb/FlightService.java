package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import entity.Flight;

@Stateless
@LocalBean
public class FlightService {

	@PersistenceContext(unitName = "flight_6_jpa_jsf")
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

    public void removeFlight(long id) {
        Flight flight = em.find(Flight.class, id);
        em.remove(flight);
    }

   

}
