package models;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {

    private String name;
    private String surname;
    private String currencyCode;
    private String email;
    private String username;
    private String password;

    public User() {
        Faker faker = new Faker();
        this.name = faker.name().firstName();
        this.surname = faker.name().lastName();
        this.username = name + "." + surname;
        this.email = username + "@gmail.com";
        this.currencyCode = faker.currency().code();
        this.password = faker.internet().password();
    }

    public String getUserBody(){
        return "{\n" +
                "  \"currency_code\": \"" + currencyCode + "\",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"password_change\": \"" + password + "\",\n" +
                "  \"password_repeat\": \"" + password + "\",\n" +
                "  \"surname\": \"" + surname + "\",\n" +
                "  \"username\": \"" + username + "\"\n" +
                "}";
    }
}
