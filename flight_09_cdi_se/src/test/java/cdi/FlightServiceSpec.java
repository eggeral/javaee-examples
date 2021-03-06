package cdi;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import storage.XmlStorage;

import static org.junit.Assert.*;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

public class FlightServiceSpec {
    private Weld weld;
    private WeldContainer container;

    @Before
    public void setup() {
        weld = new Weld().disableDiscovery()
                .addBeanClass(FlightService.class)
                .addBeanClass(DummyStorage.class);
        container = weld.initialize();
    }

    @After
    public void cleanup() {
        weld.shutdown();
    }


    @Test
    public void canAddNewFlights() throws IOException {

        // given
        FlightService client = container.instance().select(FlightService.class).get();

        Flight flight = new Flight();
        flight.setId(10);
        flight.setFlightNumber("OS456");
        flight.setFromAirport("GRZ");
        flight.setToAirport("VIE");

        // when
        client.addFlight(flight);

        Flight result = client.getFlight(10);

        // then
        assertThat(result.getFlightNumber(), is("OS456"));
        assertThat(result.getFromAirport(), is("GRZ"));
        assertThat(result.getToAirport(), is("VIE"));
        assertThat(result.getId(), is(10L));
    }

    @Test
    public void canSearchForFlightsById() {
        // given
        FlightService client = container.instance().select(FlightService.class).get();
        Flight flight = new Flight();
        flight.setId(100);
        flight.setFlightNumber("OS123");
        flight.setFromAirport("GRZ");
        flight.setToAirport("VIE");
        client.addFlight(flight);

        // when
        Flight result = client.getFlight(flight.getId());

        // then
        assertThat(result.getFlightNumber(), is("OS123"));
        assertThat(result.getFromAirport(), is("GRZ"));
        assertThat(result.getToAirport(), is("VIE"));
        assertThat(result.getId(), is(flight.getId()));

        // when / then
        assertThat(client.getFlight(-999), is(nullValue()));
    }


    @Test
    public void canDeleteFlights() {
        // given
        FlightService client = container.instance().select(FlightService.class).get();
        Flight flight = new Flight();
        flight.setId(99);
        flight.setFlightNumber("OS123");
        flight.setFromAirport("GRZ");
        flight.setToAirport("VIE");
        client.addFlight(flight);

        // when / then
        client.deleteFlight(flight.getId());
        assertThat(client.getFlight(flight.getId()), is(nullValue()));

    }

}
