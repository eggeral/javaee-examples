package rest;

import entity.Flight;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FlightService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class FlightResourceSpec {

    private static EntityManagerFactory factory;
    private EntityManager em;

    @BeforeClass
    public static void setupEntityManagerFactory() {
        factory = Persistence.createEntityManagerFactory("test");
    }


    @Before
    public void createEntityManager() {
        em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    @After
    public void closeEntityManager() {
        em.getTransaction().commit();
        em.close();
    }


    @Test
    public void flightsCanBeAdded() throws Exception {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);

        Flight flight = new Flight("OS202", "GRZ", "DUS");

        // when
        Flight newFlight = target.createFlight(flight);

        // then
        assertThat(newFlight.getId(), is(not(0L)));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

    }

    @Test
    public void flightsCanBeUpdated() throws Exception {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        Flight oldFlight = target.createFlight(new Flight("ABC1", "FROM1", "TO1"));

        // when
        Flight newFlight = target.updateFlight(oldFlight.getId(), new Flight("ABC2", "FROM2", "TO2"));

        // then
        assertThat(newFlight.getId(), is(oldFlight.getId()));
        assertThat(newFlight.getFlightNumber(), is("ABC2"));
        assertThat(newFlight.getFromAirport(), is("FROM2"));
        assertThat(newFlight.getToAirport(), is("TO2"));

        newFlight = target.getFlight(oldFlight.getId()); // paranoia
        assertThat(newFlight.getId(), is(oldFlight.getId()));
        assertThat(newFlight.getFlightNumber(), is("ABC2"));
        assertThat(newFlight.getFromAirport(), is("FROM2"));
        assertThat(newFlight.getToAirport(), is("TO2"));

    }


    @Test(expected = NotFoundException.class)
    public void tryingToUpdateAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        Long id = -9999L;

        // when
        target.updateFlight(id, new Flight("OS202", "GRZ", "DUS"));

    }

    @Test
    public void canGetAFlightByItsId() {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        Long id = target.createFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // when
        Flight flight = target.getFlight(id);

        // then
        assertThat(flight.getId(), is(not(0L)));
        assertThat(flight.getFlightNumber(), is("OS202"));
        assertThat(flight.getFromAirport(), is("GRZ"));
        assertThat(flight.getToAirport(), is("DUS"));

    }

    @Test(expected = NotFoundException.class)
    public void tryingToGetAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        Long id = -9999L;

        // when
        target.getFlight(id);

    }


    @Test
    public void aFlightCanBeRemoved() {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        Long id = target.createFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // when
        target.getFlight(id);
        target.deleteFlight(id);

        // then
        try {
            target.getFlight(id);
            fail("Flight: " + id + " was not removed.");
        } catch (NotFoundException ex) {
            assertThat(true, is(true));
        }

    }

    @Test(expected = NotFoundException.class)
    public void tryingToRemoveAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        Long id = -9999L;

        // when
        target.deleteFlight(id);

    }


    @Test
    public void canGetAListOfAllFlights() {
        // cleanup
        FlightService service = new FlightService();
        service.em = em;
        FlightResource target = new FlightResource();
        target.setFlightService(service);
        target.getFlights().forEach(flight -> target.deleteFlight(flight.getId()));

        // given
        Flight flight1 = target.createFlight(new Flight("OS202", "GRZ", "DUS"));
        Flight flight2 = target.createFlight(new Flight("LH1234", "GRZ", "VIE"));

        // when
        List<Flight> newFlights = target.getFlights();

        // then
        assertThat(newFlights.size(), is(2));
        assertThat(newFlights, hasItem(flight1));
        assertThat(newFlights, hasItem(flight2));

    }


}
