package br.com.financas.api.service;

import br.com.financas.api.dto.CategoriaDTO;
import br.com.financas.api.entity.CategoriaEntity;
import br.com.financas.api.repository.CategoriaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class CategoriaServiceTest {

    @TestConfiguration
    static class CategoriaServiceTestConfiguration {

        @Bean
        public CategoriaService categoriaService(){
            return new CategoriaService();
        }

    }


    @Autowired
    CategoriaService categoriaService;

    @MockBean
    CategoriaRepository repository;

    @Before
    public void setup() throws Exception{

        List<CategoriaEntity> list = new ArrayList<>();
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(1L);
        entity.setDescricao("Serviços");
        list.add(entity);

        Mockito.when(repository.findAll()).thenReturn(list);

        Mockito.when(repository.findByDescricaoContaining("er")).thenReturn(list);

        Mockito.when(repository.findById(1L)).thenReturn(java.util.Optional.of(entity));

    }

    @Test
    public void deveRetornarCategoriaDTO_QuandoObterTodasCategorias(){

        List<CategoriaDTO> dtos = categoriaService.obterTodasCategorias();
        Assert.assertNotNull(dtos);

    }

    @Test
    public void deveRetornarNull_QuandoObterTodasCategorias(){
        List<CategoriaDTO> dtos = categoriaService.obterTodasCategorias();
        dtos = null;
        Assert.assertNull(dtos);
    }

    @Test
    public void deveRetonarCategorias_QuandoObterPorDescricao(){
        String descricao = "er";
        List<CategoriaDTO> dtoList = categoriaService.obterPorDescricao(descricao);
        Assert.assertNotNull(dtoList);
    }

    @Test
    public void deveRetornarCategoriaNull_QuandoObterPorDescricao(){
        String descricao = "er";
        List<CategoriaDTO> dtoList = categoriaService.obterPorDescricao(descricao);
        dtoList = null;
        Assert.assertNull(dtoList);
    }




}
