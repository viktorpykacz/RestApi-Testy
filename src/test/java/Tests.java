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
    }

    @Test(groups={"Przychody"})
    public void Check_If_Get_Przychody_Returns_200_OK()
    {
        given()
                .when()
                .get("/przychody")
                .then()
                .statusCode(200);

        System.out.println("Check_If_Get_Przychody_Returns_200_OK");
    }

    @Test(dependsOnGroups = "Przychody")
    public void Check_If_Json_Przychody_Is_OK()
    {
        given()
                .when()
                .get("/przychody")
                .then()
                .body(containsString("dataWystawieniaFaktury"))
                .body(containsString("terminPlatnosci"))
                .body(containsString("numerFaktury"))
                .body(containsString("nipKlienta"))
                .body(containsString("opisFaktury"))
                .body(containsString("wartoscNetto"))
                .body(containsString("wartoscVat"))
                .body(containsString("wartoscBrutto"));
        System.out.println("Check_If_Json_Przychody_Is_OK");
    }

    @Test(dependsOnMethods = "Check_If_Json_Przychody_Is_OK" )
    public void Check_If_Post_Przychody_Addes_New_Przychod()
    {
        Map<String, String> Przychod = new HashMap<>();
        Przychod.put("dataWystawieniaFaktury","31.01.2024");
        Przychod.put("terminPlatnosci","07.02.2024");
        Przychod.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        Przychod.put("nipKlienta","1132800777");
        Przychod.put("opisFaktury","TESTOWA FAKTURA AUTOMAT TWORZENIE");
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
    }

    @Test(dependsOnMethods = "Check_If_Json_Przychody_Is_OK")
    public void Check_If_Post_Przychody_Addes_New_Przychod_And_Delete_Przychody_Deletes_Przychod()
    {
        Map<String, String> Przychod = new HashMap<>();
        Przychod.put("dataWystawieniaFaktury","31.01.2024");
        Przychod.put("terminPlatnosci","07.02.2024");
        Przychod.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        Przychod.put("nipKlienta","1132800777");
        Przychod.put("opisFaktury","TESTOWA FAKTURA AUTOMAT TWORZENIE I KASOWANIE");
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
        System.out.println("Testowy przychod usuniety");
    }

    @Test(dependsOnMethods = "Check_If_Json_Przychody_Is_OK")
    public void Check_If_Post_Przychody_Addes_New_Przychod_And_Put_Przychody_Updates_Przychod()
    {
        Map<String, String> Przychod = new HashMap<>();
        Przychod.put("dataWystawieniaFaktury","31.01.2024");
        Przychod.put("terminPlatnosci","07.02.2024");
        Przychod.put("numerFaktury","FV_TESTOWA_AUTOMAT");
        Przychod.put("nipKlienta","1132800777");
        Przychod.put("opisFaktury","TESTOWA FAKTURA AUTOMAT TWORZENIE");
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
        PrzychodUpdated.put("opisFaktury","TESTOWA FAKTURA AUTOMAT TWORZENIE_UPDATED");
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
        System.out.println("Testowy przychod zaktualizowany");
    }

    @Test(groups = {"Koszty"})
    public void Check_If_Get_Koszty_Returns_200_OK()
    {
        given()
                .when()
                .get("/koszty")
                .then()
                .statusCode(200);
    }

    @Test(groups={"GodzinyPracy"})
    public void Check_If_Get_GodzinyPracy_Returns_200_OK()
    {
        given()
                .when()
                .get("/godzinyPracy")
                .then()
                .statusCode(200);
    }

    @Test(groups = {"Kontrakty"})
    public void Check_If_Get_Kontrakty_Returns_200_OK()
    {
        given()
                .when()
                .get("/kontrakty")
                .then()
                .statusCode(200);
    }

    @Test(groups = {"ZestawienieGodzinyPracy"})
    public void Check_If_Get_ZestawienieGodzinyPracy_Returns_200_OK()
    {
        given()
                .when()
                .get("/zestawienieGodzinPracy")
                .then()
                .statusCode(200);
    }

    @Test(groups = {"ZestawienieMiesieczneFirmy"})
    public void Check_If_Get_ZestawienieMiesieczneFirmy_Returns_200_OK()
    {
        given()
                .when()
                .get("/zestawienieMiesieczneFirmy")
                .then()
                .statusCode(200);
    }

    @Test(groups = {"Podatki"})
    public void Check_If_Get_Podatki_Returns_200_OK()
    {
        given()
                .when()
                .get("/podatki")
                .then()
                .statusCode(200);
    }





}
