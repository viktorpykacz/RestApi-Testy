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

public class GodzinyPracyTests extends TestListenerAdapter
{
    @BeforeClass
    public void initialSetup()
    {
        ConfigurationForTests.setupConnection("http://192.168.1.102","5000");
    }

    @Test(groups={"GodzinyPracy"})
    public void HealthCheck_GodzinyPracy_200_OK()
    {
        given()
                .when()
                .get("/GodzinyPracy")
                .then()
                .statusCode(200);
    }

    /*TODO
    Test add and delete GodzinyPracy
    Test add and update GodzinyPracy
     */


}
