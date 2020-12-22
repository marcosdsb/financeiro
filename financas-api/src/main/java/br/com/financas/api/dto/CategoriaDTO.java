package br.com.financas.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class CategoriaDTO {
	
	private Long id;
	
	@NotNull
	@Size(min = 3, max=60)
	private String descricao;

}
