package ui.page;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

@Location("index.html")
public class FlightPage {

    @FindBy(id = "saveButton")
    private WebElement saveButton;

    @FindBy(id = "cancelButton")
    private WebElement cancelButton;

    @FindBy(id = "deleteButton")
    private WebElement deleteButton;

    @FindBy(id = "flightNumberInput")
    private WebElement flightNumberInput;

    @FindBy(id = "fromInput")
    private WebElement fromInput;

    @FindBy(id = "toInput")
    private WebElement toInput;

    public void setFlightNumber(String flightNumber) {
        flightNumberInput.sendKeys(flightNumber);
    }

    public void setFrom(String from) {
        fromInput.sendKeys(from);
    }

    public void setTo(String to) {
        toInput.sendKeys(to);
    }

    public void save() {
//        guardAjax(saveButton).click();
        saveButton.click();
    }

    public void delete() {
        guardAjax(deleteButton).click();
    }

    public void cancel() {
        guardAjax(cancelButton).click();
    }

}
