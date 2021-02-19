package br.com.financas.api.controller;

import br.com.financas.api.entity.CategoriaEntity;
import br.com.financas.api.service.CategoriaService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest
public class CategoriaControllerTest {

    @Autowired
    private CategoriaController categoriaController;

    @MockBean
    private CategoriaService categoriaService;

    @BeforeEach
    public void setup(){
        RestAssuredMockMvc.standaloneSetup(this.categoriaController);
    }

    @Test
    public void retornarSucesso_quandoBuscarId(){
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(1L);
        entity.setDescricao("Serviços");

        RestAssuredMockMvc.when( this.categoriaService.bucarCategoriaPorId(1L))
                .thenRetun(entity);

    }



}
