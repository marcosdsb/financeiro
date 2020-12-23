package br.com.financas.api.repository;

import br.com.financas.api.entity.CategoriaEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository repository;

    @Test
    public void testarNaoNulo_ObterCategoria_Descricao(){
        List<CategoriaEntity> entityList = repository.findByDescricaoContainingIgnoreCase("a");

        Assert.assertNotNull(entityList);

    }

    @Test
    public void testarNulo_ObterCategoria_Descricao(){
        List<CategoriaEntity> entityList = repository.findByDescricaoContainingIgnoreCase("xyz");
        Assert.assertNotNull(entityList);
    }

    @Test
    public void testarNaoNulo_ObterTodasCategorias(){
        List<CategoriaEntity> entityList = repository.findAll();
        Assert.assertNotNull(entityList);
    }

    @Test
    public void testarNulo_ObterTodasCategorias(){
        List<CategoriaEntity> entityList = new ArrayList<>();
        Assert.assertNotNull(entityList);
    }

}
