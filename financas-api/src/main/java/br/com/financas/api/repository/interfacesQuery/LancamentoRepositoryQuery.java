package br.com.financas.api.repository.interfacesQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.financas.api.entity.LancamentoEntity;
import br.com.financas.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public Page<LancamentoEntity> filtrar(LancamentoFilter filter, Pageable page);

}
