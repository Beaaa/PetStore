package petstore;

import io.restassured.specification.Argument;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Cibelly"))
                .body("category.name", is("dog"))
                .body("tags.id", contains(2021)) // se a chave tiver uma lista, for seguida de colchetes, precisa usar o contains pra pegar o valor
                .body("tags.name", contains("sta"))
        ;
    }

    @Test
    public void consultarPet(){
        String petId = "599170";

        String name = //salvando nessa variavel a informação extraída na linha 61
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Cibelly"))
                .body("category.name", is("dog"))
                .body("tags.id", contains(2021)) // se a chave tiver uma lista, for seguida de colchetes, precisa usar o contains pra pegar o valor
                .body("tags.name", contains("sta"))
        .extract()//extraindo informação pra salvar numa variavel
                .path("name")
        ;

        System.out.println("Nome do animal: " + name );


    }

 }
