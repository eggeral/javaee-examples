package fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import restclient.FlightClient;

public class FxClient extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FlightClient client = new FlightClient();
		
		FlightsUi flightsUi = new FlightsUi();
		FlightsAction flightsAction = new FlightsAction(flightsUi, client);
		flightsUi.connectEvents(flightsAction);
		flightsAction.reloadFlights();
		
        Scene mainScene = new Scene(flightsUi.layout());
        primaryStage.setScene(mainScene);
        primaryStage.setFullScreen(false);
        primaryStage.setTitle("Flights");
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.setX(200);
        primaryStage.setY(300);
        primaryStage.show();
    }
		
}
