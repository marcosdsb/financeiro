package br.com.financas.api.mapper;

import br.com.financas.api.dto.CategoriaDTO;
import br.com.financas.api.entity.CategoriaEntity;

public class CategoriaBuildMapper {
	
	public CategoriaBuildMapper() {}

	public static CategoriaDTO converteEntityDTO( CategoriaEntity categoriaEntity ) {
		return CategoriaDTO.builder()
				.id(categoriaEntity.getId())
				.descricao(categoriaEntity.getDescricao())
				.build();
	}
	
	public static CategoriaEntity converteDtoEntity( CategoriaDTO categoriaDTO ) {
		return CategoriaEntity.builder()
				.id(categoriaDTO.getId())
				.descricao(categoriaDTO.getDescricao())
				.build();
	}

}
