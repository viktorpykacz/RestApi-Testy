import io.restassured.RestAssured;

public class ConfigurationForTests
{
    static void setupConnection(String Host, String Port)
    {
        System.out.println("Starting_loading_configuration");
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(5000);
        }
        else{
            RestAssured.port = Integer.valueOf(Port);
        }

        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/api/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = Host;
        }
        RestAssured.baseURI = baseHost;
        System.out.println("Finishing_loading_configuration");

    }
}
