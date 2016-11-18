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

@SuppressWarnings("Duplicates")
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
    public void flightsCanBeAdded(@InitialPage FlightListPage flightListPage) {
        // given
        assertThat(flightListPage.containsFlight("ABC1", "FROM1", "TO1"), is(false));
        flightListPage.addNewFlight();

        // when
        flightPage.setFlightNumber("ABC1");
        flightPage.setFrom("FROM1");
        flightPage.setTo("TO1");
        flightPage.save();

        // then
        assertThat(flightListPage.containsFlight("ABC1", "FROM1", "TO1"), is(true));
    }

    @Test
    public void flightsCanBeDeleted(@InitialPage FlightListPage flightListPage) {
        // given
        assertThat(flightListPage.containsFlight("ABC2", "FROM2", "TO2"), is(false));
        flightListPage.addNewFlight();
        flightPage.createFlight("ABC2", "FROM2", "TO2");
        assertThat(flightListPage.containsFlight("ABC2", "FROM2", "TO2"), is(true));

        // when
        flightListPage.editFlight("ABC2");
        flightPage.delete();

        // then
        assertThat(flightListPage.containsFlight("ABC2", "FROM2", "TO2"), is(false));
    }

    @Test
    public void flightsCanBeUpdated(@InitialPage FlightListPage flightListPage) {
        // given
        assertThat(flightListPage.containsFlight("ABC3", "FROM3", "TO3"), is(false));
        flightListPage.addNewFlight();
        flightPage.createFlight("ABC3", "FROM3", "TO3");
        assertThat(flightListPage.containsFlight("ABC3", "FROM3", "TO3"), is(true));

        // when
        flightListPage.editFlight("ABC3");
        flightPage.setFlightNumber("ABC4");
        flightPage.setFrom("FROM4");
        flightPage.setTo("TO4");
        flightPage.save();

        // then
        assertThat(flightListPage.containsFlight("ABC3", "FROM3", "TO3"), is(false));
        assertThat(flightListPage.containsFlight("ABC4", "FROM4", "TO4"), is(true));
    }

    @Test
    public void updatingAFlightCanBeCanceled(@InitialPage FlightListPage flightListPage) {
        // given
        assertThat(flightListPage.containsFlight("ABC5", "FROM5", "TO5"), is(false));
        flightListPage.addNewFlight();
        flightPage.createFlight("ABC5", "FROM5", "TO5");
        assertThat(flightListPage.containsFlight("ABC5", "FROM5", "TO5"), is(true));

        // when
        flightListPage.editFlight("ABC5");
        flightPage.setFlightNumber("ABC6");
        flightPage.setFrom("FROM6");
        flightPage.setTo("TO6");
        flightPage.cancel();

        // then
        assertThat(flightListPage.containsFlight("ABC5", "FROM5", "TO5"), is(true));
        assertThat(flightListPage.containsFlight("ABC6", "FROM6", "TO6"), is(false));

    }

    @Test
    public void addingAFlightCanBeCanceled(@InitialPage FlightListPage flightListPage) {
        // given
        assertThat(flightListPage.containsFlight("ABC7", "FROM7", "TO7"), is(false));
        flightListPage.addNewFlight();

        // when
        flightPage.setFlightNumber("ABC7");
        flightPage.setFrom("FROM7");
        flightPage.setTo("TO7");
        flightPage.cancel();

        // then
        assertThat(flightListPage.containsFlight("ABC7", "FROM7", "TO7"), is(false));
    }

}
