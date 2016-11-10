package rest;

import entity.Flight;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import service.FlightService;
import swarm.SwarmDeployment;

import javax.ws.rs.*;
import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SuppressWarnings("Duplicates")
@RunWith(Arquillian.class)
@RunAsClient
public class FlightResourceTest {

    @Path("flights")
    private interface FlightResourceClient {

        @GET
        @Produces("application/json")
        List<Flight> getFlights();

        @GET
        @Produces("application/json")
        @Path("{id}")
        Flight getFlight(@PathParam("id") long id);

        @DELETE
        @Path("{id}")
        void removeFlight(@PathParam("id") long id);

        @PUT
        @Consumes("application/json")
        @Produces("application/json")
        Flight putFlight(Flight flight);
    }

    private static final String RESOURCE_PREFIX = ApplicationConfig.class.getAnnotation(ApplicationPath.class).value().substring(1);

    @Deployment(testable = false)
    public static JAXRSArchive createDeployment() {
        return SwarmDeployment.createDeployment();
    }

    @ArquillianResource
    private URL deploymentUrl;

    private ResteasyClient resteasyClient;
    private FlightResourceClient client;

    @Before
    public void createRestClient() {
        resteasyClient = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = resteasyClient.target(deploymentUrl.toString() + RESOURCE_PREFIX);
        client = target.proxy(FlightResourceClient.class);
    }

    @After
    public void closeRestClient() {
        if (resteasyClient != null)
            resteasyClient.close();
    }

    @Test
    public void flightsCanBeAdded() throws Exception {

        // given
        Flight flight = new Flight("OS202", "GRZ", "DUS");

        // when
        Flight newFlight = client.putFlight(flight);

        // then
        assertThat(newFlight.getId(), is(not(0)));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFrom(), is("GRZ"));
        assertThat(newFlight.getTo(), is("DUS"));

    }

    @Test
    public void canGetAFlightByItsId() {

        // given
        Long id = client.putFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // when
        Flight flight = client.getFlight(id);

        // then
        assertThat(flight.getId(), is(not(0)));
        assertThat(flight.getFlightNumber(), is("OS202"));
        assertThat(flight.getFrom(), is("GRZ"));
        assertThat(flight.getTo(), is("DUS"));

    }

    @Test(expected = NotFoundException.class)
    public void tryingToGetAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        Long id = -9999L;

        // when
        client.getFlight(id);

    }


    @Test
    public void aFlightCanBeRemoved() {

        // given
        Long id = client.putFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // when
        client.getFlight(id);
        client.removeFlight(id);

        // then
        try {
            client.getFlight(id);
            fail("Flight: " + id + " was not removed.");
        } catch (NotFoundException ex) {
            assertThat(true, is(true));
        }

    }

    @Test(expected = NotFoundException.class)
    public void tryingToRemoveAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        Long id = -9999L;

        // when
        client.removeFlight(id);

    }


    @Test
    public void canGetAListOfAllFlights() {
        // cleanup
        List<Flight> oldFlights = client.getFlights();
        oldFlights.forEach(flight -> client.removeFlight(flight.getId()));

        // given
        Flight flight1 = client.putFlight(new Flight("OS202", "GRZ", "DUS"));
        Flight flight2 = client.putFlight(new Flight("LH1234", "GRZ", "VIE"));

        // when
        List<Flight> newFlights = client.getFlights();

        // then
        assertThat(newFlights.size(), is(2));
        assertThat(newFlights, hasItem(flight1));
        assertThat(newFlights, hasItem(flight2));

    }


}
