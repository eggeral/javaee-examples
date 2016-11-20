package service;

import entity.Flight;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.hasItem;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class FlightServiceSpec {


    @Test
    public void flightsCanBeAddedIntoTheDatabase() throws Exception {

        // given
        FlightService target = new FlightService();

        EntityManager em = mock(EntityManager.class);
        target.em = em;

        Flight flight = new Flight("OS202", "GRZ", "DUS");

        // when
        Flight newFlight = target.addFlight(flight);

        // then
        verify(em).persist(flight);
        assertThat(newFlight.getId(), is(0L));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

    }

    @Test
    public void flightsCanRetrievedFromTheDatabase() throws Exception {

        // given
        FlightService target = new FlightService();

        EntityManager em = mock(EntityManager.class);
        target.em = em;

        Flight flight = new Flight("OS202", "GRZ", "DUS");
        flight.setId(1234L);

        // when
        when(em.find(Flight.class, 1234L)).thenReturn(flight);
        Flight newFlight = target.getFlight(1234L);

        // then
        verify(em).find(Flight.class, 1234L);
        assertThat(newFlight.getId(), is(1234L));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

    }


    @Test
    public void flightsCanBeUpdatedInTheDatabase() throws Exception {

        // given
        FlightService target = new FlightService();

        EntityManager em = mock(EntityManager.class);
        target.em = em;

        Flight flight = new Flight("ABC1", "FROM1", "TO1");
        flight.setId(1234L);
        // when
        when(em.find(Flight.class, 1234L)).thenReturn(flight);
        flight = target.updateFlight(1234L, new Flight("ABC2", "FROM2", "TO2"));

        // then
        verify(em).find(Flight.class, 1234L);
        assertThat(flight.getId(), is(1234L));
        assertThat(flight.getFlightNumber(), is("ABC2"));
        assertThat(flight.getFromAirport(), is("FROM2"));
        assertThat(flight.getToAirport(), is("TO2"));

    }

    @Test
    public void itCanBeCheckedIfAFlightExists() {
        // given
        FlightService target = new FlightService();

        EntityManager em = mock(EntityManager.class);
        target.em = em;
        // when
        when(em.find(Flight.class, 1234L)).thenReturn(new Flight("ABC2", "FROM2", "TO2"));
        when(em.find(Flight.class, 5678L)).thenReturn(null);

        // then
        assertThat(target.exists(1234L), is(true));
        assertThat(target.exists(5678L), is(false));
        verify(em).find(Flight.class, 1234L);
        verify(em).find(Flight.class, 5678L);
    }

    @Test
    public void aListOfAllExistingFlightsCanBeRetrieved() {

        // given
        FlightService target = new FlightService();

        EntityManager em = mock(EntityManager.class);
        target.em = em;

        Query query = mock(Query.class);

        List<Flight> flights = new ArrayList<>();
        Flight flight1 = new Flight("OS202", "GRZ", "DUS");
        flight1.setId(1L);
        Flight flight2 = new Flight("LH1234", "GRZ", "VIE");
        flight2.setId(2L);
        flights.add(flight1);
        flights.add(flight2);

        // when
        when(em.createQuery("SELECT f from Flight f")).thenReturn(query);
        when(query.getResultList()).thenReturn(flights);
        flights = target.getFlights();

        // then
        verify(em).createQuery("SELECT f from Flight f");
        verify(query).getResultList();
        assertThat(flights.size(), is(2));
        assertThat(flights, hasItem(flight1));
        assertThat(flights, hasItem(flight2));

    }


    @Test
    public void flightsCanBeDeleted() {

        // given
        FlightService target = new FlightService();
        EntityManager em = mock(EntityManager.class);
        target.em = em;

        Flight flight = new Flight("OS202", "GRZ", "DUS");
        flight.setId(1234L);

        when(em.find(Flight.class, 1234L)).thenReturn(flight);

        // when
        target.removeFlight(1234L);

        // then
        verify(em).remove(flight);
        verify(em).find(Flight.class, 1234L);

    }
}
