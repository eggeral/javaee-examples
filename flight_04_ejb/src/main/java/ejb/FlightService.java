package ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import entity.Flight;

@Stateless
@LocalBean
public class FlightService {

	// never!! do this in an EJB
	public static List<Flight> flights = new ArrayList<>();
	
	public FlightService() {
		// we acutaly have no idea how often this gets called!
        flights.add(new Flight("OS101", "GRZ", "VIE"));
        flights.add(new Flight("LH2412", "MNC", "NYN"));
        flights.add(new Flight("TH112", "BKG", "PKT"));
    }

    public String helloWorld() {
    	return "Hello World";
    }
    
    

    public List<Flight> getFlights() {
        return flights;
    }
}
