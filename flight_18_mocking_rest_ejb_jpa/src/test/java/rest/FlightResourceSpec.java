package rest;

import entity.Flight;
import org.junit.Test;
import service.FlightService;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class FlightResourceSpec {

    @Test
    public void flightsCanBeAdded() throws Exception {

        // given
        FlightService flightService = mock(FlightService.class);

        FlightResource target = new FlightResource();
        target.setFlightService(flightService);

        Flight flight = new Flight("OS202", "GRZ", "DUS");
        when(flightService.addFlight(flight)).thenReturn(flight);

        // when
        Flight newFlight = target.createFlight(flight);

        // then
        verify(flightService).addFlight(flight);
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

    }

    @Test
    public void flightsCanBeUpdated() throws Exception {

        // given
        FlightService flightService = mock(FlightService.class);

        FlightResource target = new FlightResource();
        target.setFlightService(flightService);
        Flight oldFlight = new Flight("ABC1", "FROM1", "TO1");
        oldFlight.setId(1234L);
        Flight newFlight = new Flight("ABC2", "FROM2", "TO2");
        newFlight.setId(1234L);
        when(flightService.exists(1234L)).thenReturn(true);

        // when
        when(flightService.updateFlight(oldFlight.getId(), newFlight)).thenReturn(newFlight);
        Flight flight = target.updateFlight(oldFlight.getId(), newFlight);

        // then
        verify(flightService).updateFlight(oldFlight.getId(), newFlight);
        assertThat(flight.getId(), is(oldFlight.getId()));
        assertThat(flight.getFlightNumber(), is("ABC2"));
        assertThat(flight.getFromAirport(), is("FROM2"));
        assertThat(flight.getToAirport(), is("TO2"));

    }


    @Test(expected = NotFoundException.class)
    public void tryingToUpdateAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        FlightService flightService = mock(FlightService.class);
        FlightResource target = new FlightResource();
        target.setFlightService(flightService);
        Long id = -9999L;
        when(flightService.exists(id)).thenReturn(false);

        // when
        target.updateFlight(id, new Flight("OS202", "GRZ", "DUS"));

    }

    @Test
    public void canGetAFlightByItsId() {

        // given
        FlightService flightService = mock(FlightService.class);
        FlightResource target = new FlightResource();
        target.setFlightService(flightService);
        Flight flight = new Flight("OS202", "GRZ", "DUS");
        flight.setId(1234L);

        // when
        when(flightService.exists(1234L)).thenReturn(true);
        when(flightService.getFlight(1234L)).thenReturn(flight);
        flight = target.getFlight(1234L);

        // then
        verify(flightService).getFlight(1234L);
        assertThat(flight.getId(), is(1234L));
        assertThat(flight.getFlightNumber(), is("OS202"));
        assertThat(flight.getFromAirport(), is("GRZ"));
        assertThat(flight.getToAirport(), is("DUS"));

    }

    @Test(expected = NotFoundException.class)
    public void tryingToGetAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        FlightService flightService = mock(FlightService.class);
        FlightResource target = new FlightResource();
        target.setFlightService(flightService);
        Long id = -9999L;
        when(flightService.exists(id)).thenReturn(false);

        // when
        target.getFlight(id);

    }


    @Test
    public void aFlightCanBeRemoved() {

        // given
        FlightService flightService = mock(FlightService.class);
        FlightResource target = new FlightResource();
        target.setFlightService(flightService);

        // when
        when(flightService.exists(1234L)).thenReturn(true);
        target.deleteFlight(1234L);

        // then
        verify(flightService).removeFlight(1234L);

    }

    @Test(expected = NotFoundException.class)
    public void tryingToRemoveAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        FlightService flightService = mock(FlightService.class);
        FlightResource target = new FlightResource();
        target.setFlightService(flightService);
        Long id = -9999L;
        when(flightService.exists(id)).thenReturn(false);

        // when
        target.deleteFlight(id);

    }


    @Test
    public void canGetAListOfAllFlights() {

        // given
        FlightResource target = new FlightResource();
        FlightService flightService = mock(FlightService.class);
        target.setFlightService(flightService);

        List<Flight> flights = new ArrayList<>();
        Flight flight1 = new Flight("OS202", "GRZ", "DUS");
        flight1.setId(1L);
        Flight flight2 = new Flight("LH1234", "GRZ", "VIE");
        flight2.setId(2L);
        flights.add(flight1);
        flights.add(flight2);

        // when
        when(flightService.getFlights()).thenReturn(flights);
        List<Flight> newFlights = target.getFlights();

        // then
        verify(flightService).getFlights();
        assertThat(newFlights.size(), is(2));
        assertThat(newFlights, hasItem(flight1));
        assertThat(newFlights, hasItem(flight2));

    }


}
