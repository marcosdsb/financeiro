package br.com.financas.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.financas.api.mapper.PessoasMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financas.api.dto.PessoaDTO;
import br.com.financas.api.entity.PessoaEntity;
import br.com.financas.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	
	public PessoaDTO buscarPessoaPorId(Long id) {
		Optional<PessoaEntity> entity = repository.findById(id); 
		if(!entity.isPresent()) {
			return null;
		}
		
		PessoaDTO pessoaDTO = PessoasMapper.INSTANCE.entityToDto(entity.get());
		return pessoaDTO;
	}
	
	public PessoaDTO salvarNovaPessoa(PessoaDTO pessoaDTO) {
		
		PessoaEntity pessoaEntity =  PessoasMapper.INSTANCE.dtoToEntity(pessoaDTO);
		
		PessoaEntity pessoaSalva = repository.save(pessoaEntity);
		
		PessoaDTO pessoaSalvaDTO = PessoasMapper.INSTANCE.entityToDto(pessoaSalva);
		
		
		return pessoaSalvaDTO;
		
	}

	public List<PessoaDTO> obterPessoas() {
		
		List<PessoaEntity> pessoaEntities = repository.findAll(); 
		
		if(pessoaEntities.isEmpty()) {
			return null;
		}
		List<PessoaDTO> pessoaDTOs = new ArrayList<>();
		
		pessoaEntities.forEach(entity ->{
			PessoaDTO pessoaDTO = PessoasMapper.INSTANCE.entityToDto(entity);
			pessoaDTOs.add(pessoaDTO);  
		});
		
		return pessoaDTOs;
		
	}

	public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
		Optional<PessoaEntity> pessoaSalva = buscarPessoaPeloId(id);
		
		PessoaEntity pessoaEntity = PessoasMapper.INSTANCE.dtoToEntity(pessoaDTO);
		
		BeanUtils.copyProperties(pessoaEntity, pessoaSalva.get(), "id");

		repository.save(pessoaSalva.get());
		
		return PessoasMapper.INSTANCE.entityToDto(pessoaSalva.get());
	}
	
	public void atualizarStatus(Long id, Boolean status) {
		Optional<PessoaEntity> pessoaSalva = buscarPessoaPeloId(id);
		pessoaSalva.get().setStatus(status);
		repository.save(pessoaSalva.get());
		
	}

	private Optional<PessoaEntity> buscarPessoaPeloId(Long id) {
		Optional<PessoaEntity> pessoaSalva = repository.findById(id);
		if(!pessoaSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}


}
