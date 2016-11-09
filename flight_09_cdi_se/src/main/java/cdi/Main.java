package cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {
	public static void main(String[] args) {
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		
		FlightService service = container.instance().select(FlightService.class).get();
		Flight flight = new Flight();
		flight.setId(1);
		flight.setFlightNumber("OS345");
		flight.setFromAirport("GRZ");
		flight.setToAirport("VIE");

		service.addFlight(flight);
		flight = service.getFlight(flight.getId());
		System.out.println(flight);
		
		service.deleteFlight(flight.getId());
		flight = service.getFlight(flight.getId());
		System.out.println(flight);
		
		weld.shutdown();
	}
}
