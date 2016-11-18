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

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Arquillian.class)
@RunAsClient
public class SimpleUiTest {

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
    public void canOpenGoogle() {
        driver.get("http://www.google.com");
        assertThat(driver.getTitle(), is("Google"));
    }

    @Test
    public void canOpenOurIndexHtml() {
        driver.get(deploymentUrl.toExternalForm() + "/index.html");
        assertThat(driver.getTitle(), is("Flight - Arquillian - AngularJS"));
    }

}
