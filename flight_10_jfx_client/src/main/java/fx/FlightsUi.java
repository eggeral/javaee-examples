package fx;

import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import restclient.Flight;

public class FlightsUi {
	private TableView<Flight> flightsTable = new TableView<>();
	private Button addButton = new Button("Add Flight");
	private Button removeButton = new Button("Remove Flight");
	
	public Parent layout() {
		BorderPane root = new BorderPane();
		flightsTable.setEditable(false);

		TableColumn<Flight, String> flightNumberCol = new TableColumn<>("Flight Number");
		flightNumberCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("flightNumber"));
		flightsTable.getColumns().add(flightNumberCol);

		TableColumn<Flight, String> fromCol = new TableColumn<>("From");
		fromCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("fromAirport"));
		flightsTable.getColumns().add(fromCol);

		TableColumn<Flight, String> toCol = new TableColumn<>("To");
		toCol.setCellValueFactory(new PropertyValueFactory<Flight, String>("toAirport"));
		flightsTable.getColumns().add(toCol);

		root.setCenter(flightsTable);
		ToolBar toolbar = new ToolBar(addButton, removeButton);
		root.setTop(toolbar);
		return root;
	}
	
	public void connectEvents(FlightsAction action) {
		addButton.setOnAction(event -> {
			action.addFlight();
		});
		
		removeButton.setOnAction(event -> {
			Flight selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
			action.removeFlight(selectedFlight);
		});
		
	}
	
	public void updateFlightsTableItems(ObservableList<Flight> flights) {
		flightsTable.setItems(flights);
	}

}
