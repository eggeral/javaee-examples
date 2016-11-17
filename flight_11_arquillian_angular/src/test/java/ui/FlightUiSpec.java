package ui;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import swarm.SwarmDeployment;

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
    public void testOpeningHomePage() {
        driver.get("http://www.google.com");

        String pageTitle = driver.getTitle();

        Assert.assertEquals(pageTitle, "Google");
    }

    @Test
    public void testTest() {
        driver.get(deploymentUrl.toExternalForm() + "index.html");

        String pageTitle = driver.getTitle();

        Assert.assertEquals(pageTitle, "Arquillian - AngularJS");
    }

}
