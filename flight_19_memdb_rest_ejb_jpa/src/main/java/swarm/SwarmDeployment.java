package swarm;

import entity.Flight;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import rest.ApplicationConfig;
import rest.FlightResource;
import service.FlightService;

public class SwarmDeployment {
    public static JAXRSArchive createDeployment(String persistence) {
        JAXRSArchive archive = ShrinkWrap.create(JAXRSArchive.class, "test.war")
                .addResource(FlightResource.class)
                .addAsResource(persistence, "META-INF/persistence.xml")
                .addClass(ApplicationConfig.class)
                .addPackage(Flight.class.getPackage())
                .addPackage(FlightService.class.getPackage())
                .staticContent();

        System.out.println(archive.toString(true));

        return archive;

    }
}
