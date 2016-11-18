package ui.page;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("index.html")
public class FlightListPage {

    @FindBy(id = "addNewFlightButton")
    private WebElement addNewFlightButton;

    public void addNewFlight() {
        guardAjax(addNewFlightButton).click();
    }

}
