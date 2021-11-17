package ru.test;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class UrlImageTests extends BaseTest {

    String URL = "https://static9.depositphotos.com/1006753/1183/i/600/depositphotos_11836055-stock-photo-cup-of-coffee.jpg";
    String URL2 = "https://ioiiuuoj---";
    String URL3 = "https://static9.depositphotos.com/1006753/1183/i/600.jpg";


    @Test
    void uploadUrlImage() {
         given()
                .headers("Authorization", token)
                .multiPart("image",URL)
                .expect()
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");


    }

    @Test
    void favoriteUrlImage() {
        given()
                .header("Authorization", token)
                .baseUri("https://api.imgur.com/3/image")
                .multiPart("image",  URL)
                .expect()
        .body("data", equalTo("favorited"))
                .body("success", equalTo(true))
                .statusCode(200)
                .when()
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", "AO7iHMP")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath();
    }




    @Test
    void uploadImageUncorrectUrl() {
        given()
                .headers("Authorization", token)
                .multiPart("image", URL2)
                .expect()
                .statusCode(400)
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




    @Test
    void uploadImageBrokenUrl() {
        given()
                .headers("Authorization", token)
                .multiPart("image", URL3)
                .expect()
                .statusCode(400)
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

    @Test
    void urlImageDeletionAuthed() {
        given()
                .header("Authorization", token)
                .baseUri("https://api.imgur.com/3/image")
                .multiPart("image",  URL)
                .expect()
                .body("success", equalTo(true))
                .statusCode(200)
                .when()
                .delete("https://api.imgur.com/3/image/{imageHash}", "AO7iHMP")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath();
    }


    @Test
    void favoriteAndDeletedUrlImage() {
        given()
                .header("Authorization", token)
                .baseUri("https://api.imgur.com/3/image")
                .multiPart("image",  URL)
                .expect()
                .statusCode(404)
                .when()
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", "AO7iHMP")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath();
    }



}





