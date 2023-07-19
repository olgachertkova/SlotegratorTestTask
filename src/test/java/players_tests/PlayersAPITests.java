package players_tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static helpers.APIHelper.*;

@Feature("Players API Tests")
public class PlayersAPITests extends APITestBase {

    @Description("Получить токен пользователя")
    @Parameters({"email", "password"})
    @Test
    public void getUserTokenTest(String email, String password) {
        Response response = authorization(email, password);
        String token = response.jsonPath().getString("accessToken");
        System.out.println(token);
        Assert.assertEquals(response.statusCode(), 201, "Status code is not 201");
        Assert.assertTrue(response.getBody().asString().contains("accessToken"));
    }


    @Description("Зарегистрировать игроков (12 штук)")
    @Parameters({"email", "password"})
    @Test(invocationCount = 12)
    public void createUsersTest(String email, String password) {
//        preconditions
        String accessToken = getUserToken(email, password);
        User user = new User();
        String userBody = user.getUserBody();
//        test
        Response response = createUser(accessToken, userBody);
        Assert.assertEquals(response.statusCode(), 201, "Status code is not 201");
        Assert.assertEquals(response.jsonPath().getString("username"), user.getUsername());
        Assert.assertEquals(response.jsonPath().getString("email"), user.getEmail());
        Assert.assertEquals(response.jsonPath().getString("name"), user.getName());
        Assert.assertEquals(response.jsonPath().getString("surname"), user.getSurname());
    }


    @Description("Запросить данные профиля созданного игрока")
    @Parameters({"email", "password"})
    @Test
    public void getOneUserDataTest(String email, String password) {
//        preconditions
        String accessToken = getUserToken(email, password);
        User user = new User();
        String userBody = user.getUserBody();
        Response createUserResponse = createUser(accessToken, userBody);
        Assert.assertEquals(createUserResponse.statusCode(), 201, "Status code is not 201");
//        test
        Response getUserResponse = getUserByEmail(accessToken, user.getEmail());
        Assert.assertEquals(getUserResponse.statusCode(), 201, "Status code is not 201");
        Assert.assertEquals(getUserResponse.jsonPath().getString("username"), user.getUsername());
        Assert.assertEquals(getUserResponse.jsonPath().getString("email"), user.getEmail());
        Assert.assertEquals(getUserResponse.jsonPath().getString("name"), user.getName());
        Assert.assertEquals(getUserResponse.jsonPath().getString("surname"), user.getSurname());
    }

    @Description("Запросить данные всех пользователей и отсортировать их по имени")
    @Parameters({"email", "password"})
    @Test
    public void getAllUsersDataTest(String email, String password) {
//        preconditions
        String accessToken = getUserToken(email, password);
        User user = new User();
        String userBody = user.getUserBody();
        createUser(accessToken, userBody);
//        test
        Response getAllUsersResponse = getAllUsers(accessToken);
        Assert.assertEquals(getAllUsersResponse.statusCode(), 200, "Status code is not 200");
        System.out.println(getAllUsersResponse.getBody().jsonPath().getString("sort{it.name}"));
        int amount = Integer.parseInt(getAllUsersResponse.getBody().jsonPath().getString("$.size()"));
        Assert.assertTrue(amount > 0, "Not all users have been returned. Current amount is " + amount);
    }


    @Description("Удалить всех ранее созданных пользователей. Запросить список всех пользователей и убедиться что он пустой")
    @Parameters({"email", "password"})
    @Test
    public void deleteAllUsersTest(String email, String password) {
//        preconditions
        String accessToken = getUserToken(email, password);
        User user = new User();
        String userBody = user.getUserBody();
        createUser(accessToken, userBody);
        Response getAllUsersResponse = getAllUsers(accessToken);
//        test
        List<String> list = getAllUsersResponse.getBody().jsonPath().getList("id");
        for (String id : list) {
            deleteUserByID(accessToken, id);
        }
        Response getAllUsersAfterDeleteResponse = getAllUsers(accessToken);
        int amount = Integer.parseInt(getAllUsersAfterDeleteResponse.getBody().jsonPath().getString("$.size()"));
        Assert.assertEquals(amount, 0, "Not all users have been deleted. Current amount is " + amount);
    }
}
