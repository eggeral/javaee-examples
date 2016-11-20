package rest;

import entity.Flight;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
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
public class FlightResourceIntegrationTest {

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
        void deleteFlight(@PathParam("id") long id);

        @POST
        @Consumes("application/json")
        @Produces("application/json")
        Flight createFlight(Flight flight);

        @PUT
        @Consumes("application/json")
        @Produces("application/json")
        @Path("{id}")
        Flight updateFlight(@PathParam("id") long id, Flight flight);
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
        Flight newFlight = client.createFlight(flight);

        // then
        assertThat(newFlight.getId(), is(not(0L)));
        assertThat(newFlight.getFlightNumber(), is("OS202"));
        assertThat(newFlight.getFromAirport(), is("GRZ"));
        assertThat(newFlight.getToAirport(), is("DUS"));

    }

    @Test
    public void flightsCanBeUpdated() throws Exception {

        // given
        Flight oldFlight = client.createFlight(new Flight("ABC1", "FROM1", "TO1"));

        // when
        Flight newFlight = client.updateFlight(oldFlight.getId(), new Flight("ABC2", "FROM2", "TO2"));

        // then
        assertThat(newFlight.getId(), is(oldFlight.getId()));
        assertThat(newFlight.getFlightNumber(), is("ABC2"));
        assertThat(newFlight.getFromAirport(), is("FROM2"));
        assertThat(newFlight.getToAirport(), is("TO2"));

        newFlight = client.getFlight(oldFlight.getId()); // paranoia
        assertThat(newFlight.getId(), is(oldFlight.getId()));
        assertThat(newFlight.getFlightNumber(), is("ABC2"));
        assertThat(newFlight.getFromAirport(), is("FROM2"));
        assertThat(newFlight.getToAirport(), is("TO2"));

    }


    @Test(expected = NotFoundException.class)
    public void tryingToUpdateAFlightWhichDoesNotExistReturnsNotFound() {

        // given
        Long id = -9999L;

        // when
        client.updateFlight(id, new Flight("OS202", "GRZ", "DUS"));

    }

    @Test
    public void canGetAFlightByItsId() {

        // given
        Long id = client.createFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // when
        Flight flight = client.getFlight(id);

        // then
        assertThat(flight.getId(), is(not(0L)));
        assertThat(flight.getFlightNumber(), is("OS202"));
        assertThat(flight.getFromAirport(), is("GRZ"));
        assertThat(flight.getToAirport(), is("DUS"));

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
        Long id = client.createFlight(new Flight("OS202", "GRZ", "DUS")).getId();

        // when
        client.getFlight(id);
        client.deleteFlight(id);

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
        client.deleteFlight(id);

    }


    @Test
    public void canGetAListOfAllFlights() {
        // cleanup
        List<Flight> oldFlights = client.getFlights();
        oldFlights.forEach(flight -> client.deleteFlight(flight.getId()));

        assertThat(client.getFlights().size(), is(0));

        // given
        Flight flight1 = client.createFlight(new Flight("OS202", "GRZ", "DUS"));
        Flight flight2 = client.createFlight(new Flight("LH1234", "GRZ", "VIE"));

        // when
        List<Flight> newFlights = client.getFlights();

        // then
        assertThat(newFlights.size(), is(2));
        assertThat(newFlights, hasItem(flight1));
        assertThat(newFlights, hasItem(flight2));

    }


}
