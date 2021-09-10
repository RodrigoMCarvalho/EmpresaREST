package com.empresaRest.controller;

import com.empresaRest.model.Colaborador;
import com.empresaRest.model.Setor;
import com.empresaRest.util.ColaboradorCreator;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations ="classpath:/application-test.properties")
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ColaboradorControllerTest  {

    @LocalServerPort
    protected int porta;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = porta;   //configuracao para o RestAssured funcionar
    }

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
            .header("Location", Matchers.equalTo("http://localhost:" + porta + "/v1/colaboradores/3"))
            .body("id", Matchers.equalTo(3),
                    "nome", Matchers.equalTo("Rodrigo"),
                    "cpf", Matchers.equalTo("692.342.920-06"));
    }

}