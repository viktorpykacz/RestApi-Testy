import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestListener;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.*;

public class Tests extends TestListenerAdapter {

    @BeforeClass
    public static void setup() {
        System.out.println("Starting_loading_configuration");
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(5000);
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/api/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://192.168.1.102";
        }
        RestAssured.baseURI = baseHost;
        System.out.println("Finishing_loading_configuration");
    }

    @Test(groups={"Przychody"})
    public void Test_HealthCheck_Przychody_200_OK()
    {
        System.out.println("Starting_Test_HealthCheck_Przychody_200_OK");
        given()
                .when()
                .get("/przychody")
                .then()
                .statusCode(200);

        System.out.println("Finishing_Test_HealthCheck_Przychody_200_OK");
    }

    @Test(groups={"Przychody"})
    public void Test_Add_Przychod()
    {
        System.out.println("Starting_Test_Add_Przychod");
        Map<String, String> Przychod = new HashMap<>();
        Przychod.put("dataWystawieniaFaktury","31.01.2024");
        Przychod.put("terminPlatnosci","07.02.2024");
        Przychod.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        Przychod.put("nipKlienta","1132800777");
        Przychod.put("opisFaktury","TESTOWY PRZYCHOD AUTOMAT TWORZENIE");
        Przychod.put("wartoscNetto","10000");
        Przychod.put("wartoscVat","2300");
        Przychod.put("wartoscBrutto","12300");
        System.out.println("Finishing_Test_Add_Przychod");
    }

    @Test(groups={"Przychody"})
    public void Test_Add_And_Delete_Przychod()
    {
        System.out.println("Test_Add_And_Delete_Przychod");
        Map<String, String> Przychod = new HashMap<>();
        Przychod.put("dataWystawieniaFaktury","31.01.2024");
        Przychod.put("terminPlatnosci","07.02.2024");
        Przychod.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        Przychod.put("nipKlienta","1132800777");
        Przychod.put("opisFaktury","TESTOWY PRZYCHOD AUTOMAT TWORZENIE I KASOWANIE");
        Przychod.put("wartoscNetto","10000");
        Przychod.put("wartoscVat","2300");
        Przychod.put("wartoscBrutto","12300");

        int IdPrzychod = given()
                .contentType("application/json")
                .body(Przychod)
                .when()
                .post("/przychody")
                .then()
                .statusCode(201)
                .extract().path("id");
        System.out.println("Testowy przychod dodany="+IdPrzychod);

        given()
                .pathParam("id", IdPrzychod)
                .when().delete("/przychody/{id}")
                .then()
                .statusCode(204);
        System.out.println("Testowy przychod "+IdPrzychod + " usuniety");
        System.out.println("Finishing_Test_Add_And_Delete_Przychod");
    }

    @Test(groups={"Przychody"})
    public void Test_Add_And_Update_Przychod()
    {
        System.out.println("Starting_Test_Add_And_Update_Przychod");
        Map<String, String> Przychod = new HashMap<>();
        Przychod.put("dataWystawieniaFaktury","31.01.2024");
        Przychod.put("terminPlatnosci","07.02.2024");
        Przychod.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        Przychod.put("nipKlienta","1132800777");
        Przychod.put("opisFaktury","TESTOWY PRZYCHOD AUTOMAT TWORZENIE");
        Przychod.put("wartoscNetto","10000");
        Przychod.put("wartoscVat","2300");
        Przychod.put("wartoscBrutto","12300");

        int IdPrzychod = given()
                .contentType("application/json")
                .body(Przychod)
                .when()
                .post("/przychody")
                .then()
                .statusCode(201)
                .extract().path("id");
        System.out.println("Testowy przychod dodany="+IdPrzychod);

        Map<String, String> PrzychodUpdated = new HashMap<>();

        PrzychodUpdated.put("id",Integer.toString(IdPrzychod));
        PrzychodUpdated.put("dataWystawieniaFaktury","31.01.2024");
        PrzychodUpdated.put("terminPlatnosci","01.02.2024");
        PrzychodUpdated.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        PrzychodUpdated.put("nipKlienta","1132800777");
        PrzychodUpdated.put("opisFaktury","TESTOWY PRZYCHOD AUTOMAT TWORZENIE_UPDATED");
        PrzychodUpdated.put("wartoscNetto","20000");
        PrzychodUpdated.put("wartoscVat","4600");
        PrzychodUpdated.put("wartoscBrutto","24600");

        given()
                .contentType("application/json")
                .body(PrzychodUpdated)
                .when()
                .put("/przychody/"+IdPrzychod)
                .then()
                .statusCode(204);
        System.out.println("Testowy przychod "+IdPrzychod + " zaktualizowany");
        System.out.println("Finishing_Test_Add_And_Update_Przychod");
    }

    @Test(groups = {"Koszty"})
    public void HealthCheck_Koszty_200_OK()
    {
        given()
                .when()
                .get("/koszty")
                .then()
                .statusCode(200);
    }

    /*TODO
    Test add koszty
    Test add and delete koszty
    Test add and update koszty
     */

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
    Test add GodzinyPracy
    Test add and delete GodzinyPracy
    Test add and update GodzinyPracy
     */

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
    Test add Kontrakty
    Test add and delete Kontrakty
    Test add and update Kontrakty
     */

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
    Test add ZestawienieGodzinyPracy
    Test add and delete ZestawienieGodzinyPracy
    Test add and update ZestawienieGodzinyPracy
     */

    @Test(groups = {"ZestawienieMiesieczneFirmy"})
    public void HealthCheck_ZestawienieMiesieczneFirmy_200_OK()
    {
        given()
                .when()
                .get("/zestawienieMiesieczneFirmy")
                .then()
                .statusCode(200);
    }

    /*TODO
    Test add ZestawienieMiesieczneFirmy
    Test add and delete ZestawienieMiesieczneFirmy
    Test add and update ZestawienieMiesieczneFirmy
     */

    @Test(groups = {"Podatki"})
    public void HealthCheck_Podatki_200_OK()
    {
        given()
                .when()
                .get("/podatki")
                .then()
                .statusCode(200);
    }

    /*TODO
    Test add podatki
    Test add and delete podatki
    Test add and update podatki
     */

    /*TODO - add method to clean up the database from requests created by tests
    test


     */





}
