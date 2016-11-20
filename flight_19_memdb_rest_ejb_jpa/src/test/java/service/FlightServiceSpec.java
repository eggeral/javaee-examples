package service;

import entity.Flight;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


public class FlightServiceSpec {

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
    public void flightsCanBeAddedIntoTheDatabase() throws Exception {

        // given
        FlightService target = new FlightService();
        target.em = em;

        Flight flight = new Flight("OS202", "GRZ", "DUS");

        // when
        Flight newFlight = target.addFlight(flight);

        // then
        assertThat(newFlight.getId(), is(not(0L)));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

    }

    @Test
    public void flightsCanRetrievedFromTheDatabase() throws Exception {

        // given
        FlightService target = new FlightService();
        target.em = em;

        Flight flight = new Flight("OS202", "GRZ", "DUS");
        Flight oldFlight = target.addFlight(flight);

        // when
        Flight newFlight = target.getFlight(oldFlight.getId());

        // then
        assertThat(newFlight.getId(), is(oldFlight.getId()));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

        // when
        newFlight = target.getFlight(-9999L);
        assertThat(newFlight, is(nullValue()));

    }


    @Test
    public void flightsCanBeUpdatedInTheDatabase() throws Exception {

        // given
        FlightService target = new FlightService();
        target.em = em;

        Flight oldFlight = target.addFlight(new Flight("ABC1", "FROM1", "TO1"));

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

    @Test
    public void itCanBeCheckedIfAFlightExists() {
        // given
        FlightService target = new FlightService();
        target.em = em;

        Flight flight = new Flight("OS202", "GRZ", "DUS");

        // when
        Flight newFlight = target.addFlight(flight);

        // then
        assertThat(target.exists(newFlight.getId()), is(true));
        assertThat(target.exists(-999), is(false));
    }

    @Test
    public void aListOfAllExistingFlightsCanBeRetrieved() {
        // cleanup
        FlightService target = new FlightService();
        target.em = em;
        target.getFlights().forEach(flight -> target.removeFlight(flight.getId()));

        // given
        Flight flight1 = target.addFlight(new Flight("OS202", "GRZ", "DUS"));
        Flight flight2 = target.addFlight(new Flight("LH1234", "GRZ", "VIE"));
        em.flush();

        // when
        List<Flight> newFlights = target.getFlights();

        // then
        assertThat(newFlights.size(), is(2));
        assertThat(newFlights, hasItem(flight1));
        assertThat(newFlights, hasItem(flight2));

    }


    @Test
    public void flightsCanBeDeleted() {

        // given
        FlightService target = new FlightService();
        target.em = em;

        // when
        Long id = target.addFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // then
        assertThat(target.getFlight(id), is(not(nullValue())));

        // when
        target.removeFlight(id);

        // then
        assertThat(target.getFlight(id), is(nullValue()));

    }

}
