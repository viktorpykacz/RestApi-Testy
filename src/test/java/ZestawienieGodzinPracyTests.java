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

public class ZestawienieGodzinPracyTests extends TestListenerAdapter
{
    @BeforeClass
    public void initialSetup()
    {
        ConfigurationForTests.setupConnection("http://192.168.1.102","5000");
    }

    @Test(groups = {"ZestawienieGodzinyPracy"})
    public void HealthCheck_ZestawienieGodzinyPracy_200_OK()
    {
        given()
                .when()
                .get("/ZestawienieGodzinPracy")
                .then()
                .statusCode(200);
    }

    /*TODO
    Test add and delete ZestawienieGodzinyPracy
    Test add and update ZestawienieGodzinyPracy
     */
}
