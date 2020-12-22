package br.com.financas.api.repository.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.financas.api.entity.LancamentoEntity;
import br.com.financas.api.repository.filter.LancamentoFilter;
import br.com.financas.api.repository.interfacesQuery.LancamentoRepositoryQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<LancamentoEntity> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEntity> criteria = builder.createQuery(LancamentoEntity.class);
		
		Root<LancamentoEntity> root = criteria.from(LancamentoEntity.class);
		
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<LancamentoEntity> query = manager.createQuery(criteria);
		
		adicionarCriteriosDaPaginacao(query, pageable);
			
		return new PageImpl<>(query.getResultList(), pageable, total(filter) )  ;
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<LancamentoEntity> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if( !StringUtils.isEmpty(filter.getDescricao()) ) {
			predicates.add(
					builder.like(
							builder.lower(root.get("descricao")), "%" + filter.getDescricao().toLowerCase()  +"%"
							)
					);
		}
		
		if(filter.getVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get("dataVencimento"), filter.getVencimentoDe())
					);
		}
		
		if(filter.getVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get("dataVencimento"), filter.getVencimentoAte())
					);
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarCriteriosDaPaginacao(TypedQuery<LancamentoEntity> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeitoRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeitoRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
		
		
	}
	
	private Long total(LancamentoFilter filter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<LancamentoEntity> root = criteriaQuery.from(LancamentoEntity.class);
		
		Predicate[] predicates = criarRestricoes(filter, criteriaBuilder, root);
		
		criteriaQuery.where(predicates);
		criteriaQuery.select(criteriaBuilder.count(root));
		
		return manager.createQuery( criteriaQuery ).getSingleResult();
	}


	

}
