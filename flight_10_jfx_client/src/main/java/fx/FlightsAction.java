package fx;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import restclient.Flight;
import restclient.FlightClient;

public class FlightsAction {

	private FlightsUi ui;
	private FlightClient client;

	public FlightsAction(FlightsUi ui, FlightClient client) {
		this.ui = ui;
		this.client = client;
	}
	
	public void reloadFlights() {
		List<Flight> flights = client.getFlights();
		ObservableList<Flight> observableFlights = FXCollections.observableList(flights);
		ui.updateFlightsTableItems(observableFlights);
	}

	public void addFlight() {
		Dialog<Flight> flightDialog = new Dialog<>();
		
		TextField flightNumber = new TextField();
		TextField from = new TextField();
		TextField to = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(new Label("Flight Number"), 0, 0);
		grid.add(flightNumber, 1, 0);
		
		grid.add(new Label("From"), 0, 1);
		grid.add(from, 1, 1);

		grid.add(new Label("To"), 0, 2);
		grid.add(to, 1, 2);
		
		flightDialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK ) {
		        return new Flight(flightNumber.getText(), from.getText(), to.getText());
		    }
		    return null;
		});


		flightDialog.getDialogPane().setContent(grid);
		flightDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		flightDialog.showAndWait().ifPresent(flight -> {
			client.addFlight(flight);
			reloadFlights();
		});
		
	}

	public void removeFlight(Flight flight) {
		if (flight != null) {
			client.deleteFlight(flight.getId());
			reloadFlights();
		}
	}


}
