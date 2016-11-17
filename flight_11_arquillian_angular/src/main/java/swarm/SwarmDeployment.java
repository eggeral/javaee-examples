package swarm;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.undertow.WARArchive;

public class SwarmDeployment {
    public static Archive createDeployment() {
        WARArchive archive = ShrinkWrap.create(WARArchive.class, "test.war");
        archive.staticContent();
        System.out.println(archive.toString(true));
        return archive;
    }
}
