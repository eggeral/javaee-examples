package ui;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import swarm.SwarmDeployment;
import ui.page.FlightListPage;

import java.net.URL;

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

    @Drone
    private WebDriver driver;

    @ArquillianResource
    private URL deploymentUrl;

    @Test
    public void canAddNewFlights(@InitialPage FlightListPage flightListPage) {
        // given
        flightListPage.addNewFlight();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
