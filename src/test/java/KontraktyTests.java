import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestListener;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.*;

public class KontraktyTests extends TestListenerAdapter
{
    @BeforeClass
    public void initialSetup()
    {
        ConfigurationForTests.setupConnection("http://192.168.1.102","5000");
    }

    @Test(groups = {"Kontrakty"})
    public void HealthCheck_Kontrakty_200_OK()
    {
        given()
                .when()
                .get("/kontrakty")
                .then()
                .statusCode(200);
    }

    /*TODO
    Test add and delete Kontrakty
    Test add and update Kontrakty
     */
}
