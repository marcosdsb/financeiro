package br.com.financas.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class PessoaDTO {
	
	private Long id;
	
	@NotNull
	@Size(min = 3, max=60)
	private String nome;
	
	@NotNull
	private boolean status;
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String uf;

}
