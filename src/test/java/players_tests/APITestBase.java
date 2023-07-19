package players_tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class APITestBase {
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        RestAssured.baseURI = "https://testslotegrator.com";
    }
}
