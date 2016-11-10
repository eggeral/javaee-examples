package swarm;

import org.wildfly.swarm.Swarm;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        // The following is only needed in order to be able to run inside an IDE.
        // The uberjar packaged by Gradle does not need this!
        List<String> mavenRepositories = Arrays.asList(
                "https://repository.jboss.org/nexus/content/groups/m2-proxy/",
                "https://repository.jboss.org/nexus/content/groups/public/"
        );

        System.setProperty("remote.maven.repo", String.join(",", mavenRepositories));
        System.setProperty("maven.download.message", "true");
        // End of stuff needed by IDEs

        Swarm swarm = new Swarm();
        swarm.start().deploy(SwarmDeployment.createDeployment());

    }
}
