package br.com.financas.api.mapper;

import br.com.financas.api.dto.PessoaDTO;
import br.com.financas.api.entity.PessoaEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PessoasMapper {

    PessoasMapper INSTANCE = Mappers.getMapper(PessoasMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nome", target = "nome"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "logradouro", target = "logradouro"),
            @Mapping(source = "numero", target = "numero"),
            @Mapping(source = "complemento", target = "complemento"),
            @Mapping(source = "bairro", target = "bairro"),
            @Mapping(source = "cep", target = "cep"),
            @Mapping(source = "cidade", target = "cidade"),
            @Mapping(source = "uf", target = "uf")
    })
    PessoaEntity dtoToEntity(final PessoaDTO pessoaDTO);

    @InheritInverseConfiguration
    PessoaDTO entityToDto(final PessoaEntity entity);

}
