package br.com.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.financas.api.entity.CategoriaEntity;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
	
	public List<CategoriaEntity> findByDescricaoContaining(String descricao);

}
