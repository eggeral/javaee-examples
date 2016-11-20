package rest;

import entity.Flight;
import org.junit.Test;
import service.FlightService;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class FlightResourceSpec {

    // Fakes are Implementations which take shortcuts.
    private static class FlightServiceFake extends FlightService {
        private List<Flight> flights = new ArrayList<>();
        private int nextId = 1;

        @Override
        public List<Flight> getFlights() {
            return flights;
        }

        @Override
        public Flight addFlight(Flight flight) {
            flight.setId(nextId);
            nextId++;
            flights.add(flight);
            return flight;
        }

        @Override
        public Flight getFlight(long id) {
            return flights.stream().filter(flight -> flight.getId() == id).findFirst().get();
        }

        @Override
        public void removeFlight(long id) {
            flights.removeIf(flight -> flight.getId() == id);
        }

        @Override
        public boolean exists(long id) {
            return flights.stream().anyMatch(flight -> flight.getId() == id);
        }

        @Override
        public Flight updateFlight(long id, Flight flight) {
            Flight oldFlight = getFlight(id);

            oldFlight.setFlightNumber(flight.getFlightNumber());
            oldFlight.setFromAirport(flight.getFromAirport());
            oldFlight.setToAirport(flight.getToAirport());

            return oldFlight;
        }
    }

    @Test
    public void flightsCanBeAdded() throws Exception {

        // given
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());

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
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
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
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
        Long id = -9999L;

        // when
        target.updateFlight(id, new Flight("OS202", "GRZ", "DUS"));

    }

    @Test
    public void canGetAFlightByItsId() {

        // given
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
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
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
        Long id = -9999L;

        // when
        target.getFlight(id);

    }


    @Test
    public void aFlightCanBeRemoved() {

        // given
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
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
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
        Long id = -9999L;

        // when
        target.deleteFlight(id);

    }


    @Test
    public void canGetAListOfAllFlights() {

        // given
        FlightResource target = new FlightResource();
        target.setFlightService(new FlightServiceFake());
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
