package com.example.catalogo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class CatalogoResourceTest {

    @Test
    void elencoProdotti() {
        given().when().get("/prodotti")
                .then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    void prodottoInesistente() {
        given().when().get("/prodotti/9999")
                .then().statusCode(404);
    }

    @Test
    void creaProdotto() {
        given().contentType(ContentType.JSON)
                .body("{\"nome\":\"Webcam HD\",\"categoria\":\"periferiche\",\"prezzo\":49.99}")
                .when().post("/prodotti")
                .then().statusCode(201)
                .body("id", notNullValue())
                .body("nome", equalTo("Webcam HD"));
    }

    @Test
    void creaProdottoNonValido() {
        given().contentType(ContentType.JSON)
                .body("{\"categoria\":\"periferiche\"}")
                .when().post("/prodotti")
                .then().statusCode(400);
    }

    @Test
    void scontoSulProdotto() {
        given().when().get("/prodotti/1/sconto?percentuale=10")
                .then().statusCode(200)
                .body("percentuale", equalTo(10))
                .body("prezzoScontato", equalTo(80.91f));
    }

    @Test
    void scontoNonValido() {
        given().when().get("/prodotti/1/sconto?percentuale=95")
                .then().statusCode(400);
    }

    @Test
    void health() {
        given().when().get("/q/health")
                .then().statusCode(200)
                .body("status", equalTo("UP"));
    }
}
