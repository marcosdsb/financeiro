package br.com.financas.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.financas.api.dto.CategoriaDTO;
import br.com.financas.api.entity.CategoriaEntity;
import br.com.financas.api.mapper.CategoriaMapper;
import br.com.financas.api.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<CategoriaDTO> obterTodasCategorias() {
		List<CategoriaEntity> categoriaEntities = categoriaRepository.findAll(); 
		
		if(categoriaEntities.isEmpty()) {
			return null;
		}

		List<CategoriaDTO> categoriaDTOs = new ArrayList<>();
		
		categoriaEntities.forEach(entity ->{
			CategoriaDTO categoriaDTO = CategoriaMapper.INSTANCE.fromCategoriaDTO(entity);
			categoriaDTOs.add(categoriaDTO);  
		});
		
		return categoriaDTOs;
	}

	public CategoriaDTO gravarNovaCategoria(CategoriaDTO categoriaDTO) {
		CategoriaEntity categoriaEntity = CategoriaMapper.INSTANCE.converterDtoEntity(categoriaDTO);
		CategoriaEntity categoriaSalva = categoriaRepository.save(categoriaEntity);
		
		return CategoriaMapper.INSTANCE.fromCategoriaDTO(categoriaSalva);
	}

	public CategoriaDTO bucarCategoriaPorId(Long id) {
		Optional<CategoriaEntity> entity = categoriaRepository.findById(id);
		if(!entity.isPresent()) {
			return null;
		}
		CategoriaDTO categoriaDTO = CategoriaMapper.INSTANCE.fromCategoriaDTO(entity.get());
		return categoriaDTO;
	}

	public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
		Optional<CategoriaEntity> categoriaSalva = obterCategoriaPorId(id);
		
		BeanUtils.copyProperties(categoriaDTO, categoriaSalva.get(), "id");
		
		CategoriaEntity categoriaEntity = categoriaRepository.save(categoriaSalva.get());
		
		CategoriaDTO cDto = CategoriaMapper.INSTANCE.fromCategoriaDTO(categoriaEntity);
		
		return cDto;
	}
	
	public void deletarCategoria(Long id) {
		categoriaRepository.deleteById(id);
	}

	private Optional<CategoriaEntity> obterCategoriaPorId(Long id) {
		Optional<CategoriaEntity> categoriaSalva = categoriaRepository.findById(id);
		if(!categoriaSalva.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoriaSalva;
	}

	public List<CategoriaDTO> obterPorDescricao(String descricao) {
		List<CategoriaEntity> entityList = categoriaRepository.findByDescricaoContaining(descricao);
		if(entityList.isEmpty()){
			return null;
		}

		List<CategoriaDTO> dtoList = new ArrayList<>();
		entityList.forEach(element -> {
			CategoriaDTO categoriaDTO = CategoriaMapper.INSTANCE.fromCategoriaDTO(element);
			dtoList.add(categoriaDTO);
		});

		return dtoList;


	}
}
