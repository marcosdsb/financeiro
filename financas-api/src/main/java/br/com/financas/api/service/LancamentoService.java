package br.com.financas.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.financas.api.dto.LancamentoDTO;
import br.com.financas.api.entity.LancamentoEntity;
import br.com.financas.api.entity.PessoaEntity;
import br.com.financas.api.mapper.LancamentoMapper;
import br.com.financas.api.repository.LancamentoRepository;
import br.com.financas.api.repository.PessoaRepository;
import br.com.financas.api.repository.filter.LancamentoFilter;
import br.com.financas.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Page<LancamentoEntity> filtrar(LancamentoFilter filter, Pageable pageable){

		Page<LancamentoEntity> entities = repository.filtrar(filter, pageable);


		if(entities.isEmpty()) {
			return null;
		}

		return entities;

	}

	public LancamentoDTO obterLancamentoId(Long id) {
		
		Optional<LancamentoEntity> entity = repository.findById(id);
		if(!entity.isPresent()) {
			return null;
		}
		
		LancamentoDTO lancamentoDTO = LancamentoMapper.INSTANCE.fromLancamentoDTO(entity.get());
		
		return lancamentoDTO;
	}

	public LancamentoDTO salvarNovoLancamento(LancamentoDTO lancamentoDTO) {
		
		Optional<PessoaEntity> pessoaEntity = pessoaRepository.findById(lancamentoDTO.getPessoa().getId());
		if(!pessoaEntity.isPresent() || !pessoaEntity.get().isStatus()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		LancamentoEntity entity = LancamentoMapper.INSTANCE.converterDtoEntity(lancamentoDTO);
		repository.save(entity);
		
		LancamentoDTO dto = LancamentoMapper.INSTANCE.fromLancamentoDTO(entity);
		
		return dto;
	}
	
	


}
