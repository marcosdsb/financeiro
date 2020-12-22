package br.com.financas.api.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.financas.api.dto.LancamentoDTO;
import br.com.financas.api.entity.LancamentoEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LancamentoMapper {
	
	LancamentoMapper INSTANCE = Mappers.getMapper(LancamentoMapper.class);
	
	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "descricao", target = "descricao"),
		@Mapping(source = "dataVencimento", target = "dataVencimento"),
		@Mapping(source = "dataPagamento", target = "dataPagamento"),
		@Mapping(source = "valor", target = "valor"),
		@Mapping(source = "observacao", target = "observacao"),
		@Mapping(source = "tipo", target = "tipo"),
		@Mapping(source = "categoria", target = "categoria"),
		@Mapping(source = "pessoa", target = "pessoa")

	})
	LancamentoEntity converterDtoEntity(final LancamentoDTO lancamentoDTO);
	
	@InheritInverseConfiguration
	LancamentoDTO fromLancamentoDTO(final LancamentoEntity lancamentoEntity);

}

