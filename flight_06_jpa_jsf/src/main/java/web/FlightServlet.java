package web;

import java.io.IOException;
import java.io.Writer;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.FlightService;
import entity.Flight;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {

	@EJB
	private FlightService flightService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Writer writer = response.getWriter();
		writer.append("<html>");
		writer.append(flightService.helloWorld()).append(", Served at: ").append(request.getContextPath());

		String id;
		if (request.getParameter("add") != null) {
			Flight flight = new Flight();
			flight.setFlightNumber("OS1234");
			flight.setFromAirport("A");
			flight.setToAirport("B");
			flightService.addFlight(flight);
			writer.append("<p>added: " + flight);
			writer.append("</p>");
		} else if ((id = request.getParameter("remove")) != null) {
			flightService.removeFlight(Integer.parseInt(id));
			writer.append("<p>removed</p>");
		} else {
			writer.append("<ul>");
			writer.append("<ul>");
			for (Flight flight : flightService.getFlights()) {
				writer.append("<li>");
				writer.append(flight.getFlightNumber());
			}
			writer.append("</ul>");
		}
		writer.append("</html>");
	}

}
