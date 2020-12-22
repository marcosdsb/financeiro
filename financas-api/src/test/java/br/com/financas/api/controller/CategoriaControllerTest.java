package br.com.financas.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import br.com.financas.api.dto.CategoriaDTO;
import br.com.financas.api.service.CategoriaService;
import io.restassured.http.ContentType;
import org.springframework.test.context.junit4.SpringRunner;

//@WebMvcTest
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaControllerTest {
	
	@Autowired
	private CategoriaController categoriaController;
	
	@MockBean
	private CategoriaService categoriaService;

	
	@BeforeEach
	public void setup() {
		standaloneSetup(categoriaController);
	}
	
	@Test
	public void deveRetornarSucesso_QuandoObterTodasCategorias() {



		when(this.categoriaService.obterTodasCategorias())
			.thenReturn(  new ArrayList<CategoriaDTO>() );
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/api/categorias")
		.then()
			.statusCode(HttpStatus.OK.value());	
	}

}
