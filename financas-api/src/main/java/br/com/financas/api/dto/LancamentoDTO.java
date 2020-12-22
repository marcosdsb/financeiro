package br.com.financas.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.financas.api.enums.TipoLancamentoEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class LancamentoDTO {
	
	private Long id;
	
	@NotNull
	@Size(min = 5, max = 60)
	private String descricao;
	
	@NotNull
	private LocalDate dataVencimento;
	
	private LocalDate dataPagamento;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	private TipoLancamentoEnum tipo;
	
	@NotNull
	private CategoriaDTO categoria;
	
	@NotNull
	private PessoaDTO pessoa;

}
