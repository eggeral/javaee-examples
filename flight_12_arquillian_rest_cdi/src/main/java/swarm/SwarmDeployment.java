package swarm;

import entity.Flight;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import rest.ApplicationConfig;
import rest.FlightResource;
import service.FlightService;

public class SwarmDeployment {
    public static JAXRSArchive createDeployment() {
        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class, "test.war")
                .addResource(FlightResource.class)
                .addClass(ApplicationConfig.class)
                .addPackage(Flight.class.getPackage())
                .addPackage(FlightService.class.getPackage());

        System.out.println(archive.toString(true));

        return archive;

    }
}
