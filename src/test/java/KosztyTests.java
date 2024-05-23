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

public class KosztyTests extends TestListenerAdapter
{
    @BeforeClass
    public void initialSetup()
    {
        ConfigurationForTests.setupConnection("http://192.168.1.102","5000");
    }

    @Test(groups = {"Koszty"})
    public void HealthCheck_Koszty_200_OK()
    {
        System.out.println("Starting_HealthCheck_Koszty_200_OK");
        given()
                .when()
                .get("/koszty")
                .then()
                .statusCode(200);
        System.out.println("Finishing_HealthCheck_Koszty_200_OK");
    }

    @Test(groups = {"Koszty"})
    public void Test_Add_And_Delete_Koszt()
    {
        System.out.println("Starting_Test_Add_And_Delete_Koszt");
        Map<String, String> Koszt = new HashMap<>();
        Koszt.put("dataWystawieniaFaktury", "05.01.2024");
        Koszt.put("numerFaktury", "05.01.2024");
        Koszt.put("nipFirmy", "1139928365");
        Koszt.put("opisKosztu", "TESTOWY KOSZT AUTOMAT TWORZENIE");
        Koszt.put("rodzajKosztu", "AUTO");
        Koszt.put("wartoscNetto", "1000");
        Koszt.put("wartoscVat", "230");
        Koszt.put("wartoscBrutto", "1230");

        int IdKoszt = given()
                .contentType("application/json")
                .body(Koszt)
                .when()
                .post("/koszty")
                .then()
                .statusCode(201)
                .extract().path("id");
        System.out.println("Testowy koszt dodany="+IdKoszt);

        given()
                .pathParam("id", IdKoszt)
                .when().delete("/koszty/{id}")
                .then()
                .statusCode(204);
        System.out.println("Testowy koszt "+IdKoszt + " usuniety");
        System.out.println("Finishing_Test_Add_And_Delete_Koszt");
    }

    @Test(groups = {"Koszty"})
    public void Test_Add_And_Update_Koszt()
    {
        System.out.println("Starting_Test_Add_And_Update_Koszt");
        Map<String, String> Koszt = new HashMap<>();
        Koszt.put("dataWystawieniaFaktury", "06.01.2024");
        Koszt.put("numerFaktury", "09.01.2024");
        Koszt.put("nipFirmy", "1139928365");
        Koszt.put("opisKosztu", "TESTOWY KOSZT AUTOMAT TWORZENIE");
        Koszt.put("rodzajKosztu", "AUTO");
        Koszt.put("wartoscNetto", "1000");
        Koszt.put("wartoscVat", "230");
        Koszt.put("wartoscBrutto", "1230");

        int IdKoszt = given()
                .contentType("application/json")
                .body(Koszt)
                .when()
                .post("/koszty")
                .then()
                .statusCode(201)
                .extract().path("id");
        System.out.println("Testowy koszt dodany="+IdKoszt);

        Map<String, String> KosztUpdated = new HashMap<>();

        KosztUpdated.put("id", Integer.toString(IdKoszt));
        KosztUpdated.put("dataWystawieniaFaktury", "05.01.2024");
        KosztUpdated.put("numerFaktury", "05.01.2024");
        KosztUpdated.put("nipFirmy", "1139928365");
        KosztUpdated.put("opisKosztu", "TESTOWY KOSZT AUTOMAT TWORZENIE_UPDATED");
        KosztUpdated.put("rodzajKosztu", "AUTO");
        KosztUpdated.put("wartoscNetto", "5000");
        KosztUpdated.put("wartoscVat", "555");
        KosztUpdated.put("wartoscBrutto", "5555");

        given()
                .contentType("application/json")
                .body(KosztUpdated)
                .when()
                .put("/przychody/"+IdKoszt)
                .then()
                .statusCode(204);

        System.out.println("Testowy koszt "+IdKoszt + " usuniety");
        System.out.println("Finishing_Test_Add_And_Delete_Koszt");
    }


}
