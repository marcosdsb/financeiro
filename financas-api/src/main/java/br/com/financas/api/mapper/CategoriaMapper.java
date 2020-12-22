package br.com.financas.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.financas.api.dto.CategoriaDTO;
import br.com.financas.api.entity.CategoriaEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {
	
CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);
	
	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "descricao", target = "descricao")

	})
	CategoriaEntity converterDtoEntity(final CategoriaDTO categoriaDTO);
	
	@InheritInverseConfiguration
	CategoriaDTO fromCategoriaDTO(final CategoriaEntity categoriaEntity);

}

