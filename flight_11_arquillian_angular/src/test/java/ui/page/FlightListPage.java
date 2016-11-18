package ui.page;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Location("index.html")
public class FlightListPage {

    @FindBy(id = "addNewFlightButton")
    private WebElement addNewFlightButton;

    @FindBy(id = "flightsTable")
    private WebElement flightTable;

    public void addNewFlight() {
        guardAjax(addNewFlightButton).click();
    }

    public boolean containsFlight(String flightNumber, String from, String to) {
        List<WebElement> rows = flightTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.get(0).getText().equals(flightNumber) &&
                    cells.get(1).getText().equals(from) &&
                    cells.get(2).getText().equals(to))
                return true;
        }
        return false;
    }
}
