package flight;

import java.util.ArrayList;
import java.util.List;

import entity.Flight;

public class FlightService {
	// never!! do this in an EJB
		public static List<Flight> flights = new ArrayList<>();
		
		public FlightService() {
			// we acutaly have no idea how often this gets called!
	        flights.add(new Flight("OS101", "GRZ", "VIE"));
	        flights.add(new Flight("LH2412", "MNC", "NYN"));
	        flights.add(new Flight("TH112", "BKG", "PKT"));
	    }
		
		public List<Flight> getFlights() {
			return flights;
		}
		
}
