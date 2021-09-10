package com.empresaRest.controller;

import com.empresaRest.EmpresaRestApplicationTests;
import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import com.empresaRest.util.ColaboradorCreator;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


public class ColaboradorControllerTest  extends EmpresaRestApplicationTests {

    @Test
    public void deveBuscaTodos() {
        given()
            .request()
            .header("Accept", ContentType.ANY)
            .header("Content-type", ContentType.JSON)
        .when()
            .get("/v1/colaboradores")
        .then()
            .log().body()
            .and()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveSalvarColaborador() {
        Setor setor = ColaboradorCreator.createSetor();
        Colaborador colaborador = ColaboradorCreator.createColaboradorToBeSaved();
        colaborador.setSetor(setor);

        given()
            .request()
            .header("Accept", ContentType.ANY)
            .header("Content-type", ContentType.JSON)
            .body(colaborador)
        .when()
            .post("/v1/colaboradores")
        .then()
            .log().headers()
        .and()
            .log().body()
        .and()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", Matchers.equalTo("http://localhost:" + super.porta + "/v1/colaboradores/3"))
            .body("id", Matchers.equalTo(3),
                    "nome", Matchers.equalTo("Rodrigo"),
                    "cpf", Matchers.equalTo("692.342.920-06"));
    }

}