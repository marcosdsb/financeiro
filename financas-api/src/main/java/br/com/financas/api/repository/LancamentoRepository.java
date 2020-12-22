package br.com.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.financas.api.entity.LancamentoEntity;
import br.com.financas.api.repository.interfacesQuery.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<LancamentoEntity, Long>, LancamentoRepositoryQuery {
	
	

}
