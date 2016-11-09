package restclient;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class FlightClientSpec {

	@Test
	public void canAddNewFlights() {
		// given
		FlightClient client = new FlightClient();
		Flight flight = new Flight();
		flight.setFlightNumber("OS456");
		flight.setFromAirport("GRZ");
		flight.setToAirport("VIE");
		
		// when
		Flight result = client.addFlight(flight);
		
		// then
		assertThat(result.getFlightNumber(), is("OS456"));
		assertThat(result.getFromAirport(), is("GRZ"));
		assertThat(result.getToAirport(), is("VIE"));
		assertThat(result.getId(), is(greaterThan(0L)));
	}

	@Test
	public void canSearchForFlightsById() {
		// given
		FlightClient client = new FlightClient();
		Flight flight = new Flight();
		flight.setFlightNumber("OS123");
		flight.setFromAirport("GRZ");
		flight.setToAirport("VIE");
		flight = client.addFlight(flight);
		
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
		FlightClient client = new FlightClient();
		Flight flight = new Flight();
		flight.setFlightNumber("OS123");
		flight.setFromAirport("GRZ");
		flight.setToAirport("VIE");
		flight = client.addFlight(flight);
		
		// when / then
		assertThat(client.deleteFlight(flight.getId()), is(true));
		assertThat(client.getFlight(flight.getId()), is(nullValue()));

	
		// when / then 
		assertThat(client.deleteFlight(flight.getId()), is(false));
		
		
	}


}
