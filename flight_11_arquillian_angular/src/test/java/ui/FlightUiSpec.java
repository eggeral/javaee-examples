package ui;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import swarm.SwarmDeployment;
import ui.page.FlightListPage;
import ui.page.FlightPage;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
@RunAsClient
public class FlightUiSpec {

    @BeforeClass
    public static void setupWebDriverAndMavenRepositories() {
        ChromeDriverManager.getInstance().setup();
    }

    @Deployment(testable = false)
    public static Archive createDeployment() {
        return SwarmDeployment.createDeployment();
    }

    @SuppressWarnings("unused")
    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL deploymentUrl;

    @SuppressWarnings("unused")
    @Page
    private FlightPage flightPage;

    @Test
    public void canAddNewFlights(@InitialPage FlightListPage flightListPage) {
        // given
        assertThat(flightListPage.containsFlight("ABC", "FROM", "TO"), is(false));
        flightListPage.addNewFlight();

        // when
        flightPage.setFlightNumber("ABC");
        flightPage.setFrom("FROM");
        flightPage.setTo("TO");
        flightPage.save();

        // then
        assertThat(flightListPage.containsFlight("ABC", "FROM", "TO"), is(true));
    }


}
