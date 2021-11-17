package ru.test;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ImageTestWithoutImage extends BaseTest{

    @Test
    void uploadWithoutImage() {
        given()
                .headers("Authorization", token)
                .expect()
                .body("success", equalTo(false))
                .body("status", equalTo(400))
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
         .getString("data.deletehash");
    }


    }






