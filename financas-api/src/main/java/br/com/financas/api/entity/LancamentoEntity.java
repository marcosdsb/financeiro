package br.com.financas.api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.financas.api.enums.TipoLancamentoEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.MODULE)
@NoArgsConstructor
@Entity
@Table(name = "lancamento")
public class LancamentoEntity {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator = "lancamento_id_seq",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "lancamento_id_seq", sequenceName = "lancamento_id_seq", allocationSize = 1)
	private Long id;
	
	@NotNull
	@Column(name = "descricao")
	private String descricao;
	
	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	@NotNull
	@Column(name = "valor")
	private BigDecimal valor;
	
	@Column(name = "observacao")
	private String observacao;
	
	@NotNull
	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoLancamentoEnum tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_ID")
	private CategoriaEntity categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_ID")
	private PessoaEntity pessoa;
	
	

}
